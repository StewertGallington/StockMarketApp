package com.plcoding.stockmarketapp.data.repository
import android.util.Log
import com.plcoding.stockmarketapp.data.csv.CsvParser
import com.plcoding.stockmarketapp.data.local.StockDatabase
import com.plcoding.stockmarketapp.data.mapper.toCompanyInfo
import com.plcoding.stockmarketapp.data.mapper.toCompanyListing
import com.plcoding.stockmarketapp.data.mapper.toCompanyListingEntity
import com.plcoding.stockmarketapp.data.remote.StockApi
import com.plcoding.stockmarketapp.domain.model.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import com.plcoding.stockmarketapp.domain.repository.StockRepository
import com.plcoding.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val parser: CsvParser<CompanyListing>,
    val introdayInfoParser: CsvParser<IntradayInfo>
) : StockRepository {
    private val dao = db.stockDao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            // Yield the Start loading
            emit(Resource.Loading(true))

            // Get the data from the local database.
            val localListings = dao.searchCompanyListings(query)

            // Yield the local data.
            emit(Resource.Success(
                localListings.map { it.toCompanyListing() }
            ))

            // If the data is empty and no query is provided, fetch from remote.
            val isDbEmpty = localListings.isEmpty() && query.isBlank()

            // If we have local data and we are not fetching remote then we should not fetch from remote.
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            // If we are not getting any remote data.
            if (shouldJustLoadFromCache) {
                // Yield a Stop loading.
                emit(Resource.Loading(false))

                // End the flow.
                return@flow
            }

            val remoteListings = try {
                // Get the data from the remote.
                val response = api.getListings()
                parser.parse(response.byteStream())
            } catch (io: IOException) {
                io.printStackTrace()
                emit(Resource.Error(io.localizedMessage ?: "An unexpected error occurred"))
                null
            } catch (http: HttpException) {
                http.printStackTrace()
                // If we get an exception, yield an error message.
                emit(Resource.Error<List<CompanyListing>>(http.localizedMessage ?: "An unexpected error occurred"))
                null
            }

            remoteListings?.let {   listings ->
                // Clear the local cache
                dao.clearCompanyListings()

                // Insert the data from the remote data.
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )

                // Yield the data from the local data.
                emit(Resource.Success(data = dao
                    .searchCompanyListings("")
                    .map { it.toCompanyListing() }
                ))

                // Yield a Stop loading.
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayData(
        fetchFromRemote: Boolean,
        symbol: String
    ): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val intradayInfo = introdayInfoParser.parse(response.byteStream())
            Log.d("StockRepositoryImpl", "getIntradayData: ${intradayInfo.size}")
            Resource.Success(intradayInfo)
        } catch (ioe: IOException) {
            ioe.printStackTrace()
            Resource.Error<List<IntradayInfo>>(ioe.localizedMessage ?: "An unexpected error occurred")
        } catch (http: HttpException) {
            http.printStackTrace()
            Resource.Error<List<IntradayInfo>>(http.localizedMessage ?: "An unexpected error occurred")
        }
    }

    override suspend fun getCompanyInfo(
        fetchFromRemote: Boolean,
        symbol: String
    ): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            val convertedCompanyInfo = result.toCompanyInfo()
            Log.d("StockRepositoryImpl", "getCompanyInfo: ${convertedCompanyInfo.name}")
            Resource.Success(convertedCompanyInfo)
        } catch (ioe: IOException) {
            ioe.printStackTrace()
            Resource.Error(ioe.localizedMessage ?: "An unexpected error occurred")
        } catch (http: HttpException) {
            http.printStackTrace()
            Resource.Error(http.localizedMessage ?: "An unexpected error occurred")
        }
    }

}
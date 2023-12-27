package com.plcoding.stockmarketapp.data.csv

import com.opencsv.CSVReader
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingParser @Inject constructor() : CsvParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val result = withContext(Dispatchers.IO) {
            val csvReader = CSVReader(InputStreamReader(stream))
            csvReader.readAll().drop(1).mapNotNull {
                    row -> CompanyListing(
                        symbol = row[0] ?: return@mapNotNull null,
                        name = row[1] ?: return@mapNotNull null,
                        exchange = row[2] ?: return@mapNotNull null,
                        assetType = row[3] ?: return@mapNotNull null,
                        ipoDate = row[4] ?: return@mapNotNull null,
                        delistingDate = row[5] ?: return@mapNotNull null,
                        status = row[6] ?: return@mapNotNull null)
            }.also {
                csvReader.close()
            }
        }

        return result
    }
}
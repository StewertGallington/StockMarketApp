package com.plcoding.stockmarketapp.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.stockmarketapp.domain.model.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import com.plcoding.stockmarketapp.domain.repository.StockRepository
import com.plcoding.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {
    var state by mutableStateOf(CompanyInfoState())
    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(true, symbol) }
            val intradayInfoResult = async { repository.getIntradayData(true, symbol) }

            when (val result = companyInfoResult.await())
            {
                is Resource.Success<*> -> {
                    state = state.copy(
                        companyInfo = result.data as CompanyInfo,
                        isLoading = false
                    )
                }
                is Resource.Error<*> -> {
                    state = state.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "An unexpected error occurred",
                        companyInfo = null
                    )
                }
                else -> Unit
            }

            when (val result = intradayInfoResult.await())
            {
                is Resource.Success<*> -> {
                    state = state.copy(
                        stockInfos = (result.data ?: emptyList<IntradayInfo>()) as List<IntradayInfo>,
                        isLoading = false
                    )
                }
                is Resource.Error<*> -> {
                    state = state.copy(
                        isLoading = false,
                        errorMessage = result.message ?: "An unexpected error occurred",
                        stockInfos = emptyList()
                    )
                }
                else -> Unit
            }

        }
    }

    fun getCompanyInfo(
        symbol: String,
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            val companyInfoResult = async { repository.getCompanyInfo(true, symbol) }
            val intradayInfoResult = async { repository.getIntradayData(true, symbol) }
        }
    }
}
package com.plcoding.stockmarketapp.presentation.company_listings

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun CompanyListingsScreen(
    navigator: DestinationsNavigator?,
    viewModel: CompanyListingsViewModel = hiltViewModel(),
) {
    CompanyListings(
        state = viewModel.state,
        onRefresh = { viewModel.onEvent(CompanyListingsEvent.Refresh) },
        onSearchQueryChange = { viewModel.onEvent(CompanyListingsEvent.OnSearchQueryChange(it)) }
    )
}
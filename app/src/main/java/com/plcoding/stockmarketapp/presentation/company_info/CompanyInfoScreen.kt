package com.plcoding.stockmarketapp.presentation.company_info

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun CompanyInfoScreen(
    navigator: DestinationsNavigator?,
    viewModel: CompanyInfoViewModel = hiltViewModel(),
){
    CompanyInfos(
        state = viewModel.state
    )
}
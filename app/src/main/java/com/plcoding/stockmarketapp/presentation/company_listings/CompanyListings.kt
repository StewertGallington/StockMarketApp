package com.plcoding.stockmarketapp.presentation.company_listings

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.plcoding.stockmarketapp.domain.model.CompanyListing

@Composable
fun CompanyListings(
    state: CompanyListingsState = CompanyListingsState(),
    onRefresh: () -> Unit = {},
    onSearchQueryChange: (query: String) -> Unit = {},
    onItemClicked: (CompanyListing) -> Unit = {},
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                onSearchQueryChange(it)
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search")
            },
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                onRefresh()
            }
        )
        {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.companies.size) { i ->
                    val company = state.companies[i]
                    CompanyItem(
                        company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClicked(company)
                            }
                            .padding(16.dp)
                    )
                    if (i < state.companies.size) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CompanyListingsPreview() {
    val context = LocalContext.current
    val companies = List(5) {
        CompanyListing(
            name = "Company $it",
            symbol = "Symbol $it",
            exchange = "Exchange $it",
            assetType = "Asset Type $it",
            delistingDate = "Delisting Date $it",
            ipoDate = "IPO Date $it",
            status = "Status $it",)
    }

    var state = CompanyListingsState()
    state = state.copy(companies = companies)

    CompanyListings(
        state,
        onRefresh = {},
        onSearchQueryChange = {},
        onItemClicked = {
            Toast.makeText(
                context,
                "Clicked ${it.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
    )
}
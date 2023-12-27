package com.plcoding.stockmarketapp.presentation.company_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.stockmarketapp.domain.model.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import com.plcoding.stockmarketapp.ui.theme.DarkBlue
import com.plcoding.stockmarketapp.ui.theme.StockMarketAppTheme
import java.time.LocalDateTime

@Composable
fun CompanyInfoDetail(state: CompanyInfoState) {
    if (state.errorMessage.isBlank()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(16.dp)
        ) {
            state.companyInfo?.let { company ->
                Text(text = company.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = company.symbol,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.padding(8.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "Industry: ${company.industry}",
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "Country: ${company.country}",
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.padding(8.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = company.description,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth())
                if (state.stockInfos.isNotEmpty())
                {
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(text = "Market Summary")
                    Spacer(modifier = Modifier.padding(16.dp))
                    StockChart(
                        state.stockInfos,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (state.errorMessage.isNotBlank()) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colors.error,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompanyInfoDetailPreview() {
    val stockInfos = listOf(
        IntradayInfo(
            close = 100f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T00:00:00")
        ),
        IntradayInfo(
            close = 200f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T01:00:00")
        ),
        IntradayInfo(
            close = 300f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T02:00:00")
        ),
        IntradayInfo(
            close = 400f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T03:00:00")
        ),
        IntradayInfo(
            close = 500f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T04:00:00")
        ),
        IntradayInfo(
            close = 600f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T05:00:00")
        ),
        IntradayInfo(
            close = 700f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T06:00:00")
        ),
        IntradayInfo(
            close = 800f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T07:00:00")
        ),
        IntradayInfo(
            close = 900f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T08:00:00")
        ),
        IntradayInfo(
            close = 1000f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T09:00:00")
        ),
        IntradayInfo(
            close = 1100f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T10:00:00")
        ),
        IntradayInfo(
            close = 1200f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T11:00:00")
        ),
        IntradayInfo(
            close = 1300f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T12:00:00")
        ),
        IntradayInfo(
            close = 1400f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T13:00:00")
        ),
        IntradayInfo(
            close = 1500f.toDouble(),
            date = LocalDateTime.parse("2021-01-01T14:00:00")
        ))

    val companyInfoState = CompanyInfoState(
        stockInfos,
        CompanyInfo(
            name = "Tesla",
            symbol = "TSLA",
            description = "Electric Vehicles",
            industry = "Automotive",
            country = "USA")
    )

    StockMarketAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            CompanyInfoDetail(companyInfoState)
        }
    }
}
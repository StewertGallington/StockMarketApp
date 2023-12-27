package com.plcoding.stockmarketapp.presentation.company_info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.stockmarketapp.domain.model.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime

@Composable
fun CompanyInfos(
    state: CompanyInfoState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        val textModifier = Modifier.fillMaxWidth().padding(8.dp)
        Text(text = "Symbol", modifier = textModifier)
        Text(text = state.companyInfo?.symbol ?: "", textModifier)
        Text(text = "Desc.", textModifier)
        Text(text = state.companyInfo?.name ?: "", textModifier)
        Spacer(modifier = Modifier.padding(8.dp))
        StockChart(state.stockInfos)
    }
}

@Composable
@Preview(showBackground = true)
fun CompanyInfoPreview() {
    var stockInfos = listOf(
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
        )
    )

    CompanyInfos(
        state = CompanyInfoState(
            companyInfo = CompanyInfo( "AAPL", "Technology", "Apple Inc.", "US", "Consumer Electronics"),
            stockInfos = stockInfos
        )
    )
}
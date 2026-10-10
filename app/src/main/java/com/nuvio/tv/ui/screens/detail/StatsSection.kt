package com.nuvio.tv.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.nuvio.tv.ui.theme.NuvioTheme
import com.nuvio.tv.ui.util.formatCurrency

@Composable
internal fun StatsSection(
    budget: Long?,
    revenue: Long?,
    upFocusRequester: FocusRequester?,
    sectionFocusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    val formattedBudget = formatCurrency(budget)
    val formattedRevenue = formatCurrency(revenue)

    if (formattedBudget == null && formattedRevenue == null) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            formattedBudget?.let { budgetText ->
                StatCard(
                    title = "Budget",
                    value = budgetText,
                    focusRequester = sectionFocusRequester,
                    upFocusRequester = upFocusRequester
                )
            }
            formattedRevenue?.let { revenueText ->
                StatCard(
                    title = "Revenue",
                    value = revenueText,
                    focusRequester = if (formattedBudget == null) sectionFocusRequester else null,
                    upFocusRequester = upFocusRequester
                )
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = null,
    upFocusRequester: FocusRequester? = null
) {
    Box(
        modifier = modifier
            .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier)
            .then(
                if (upFocusRequester != null) {
                    Modifier.focusProperties { up = upFocusRequester }
                } else {
                    Modifier
                }
            )
            .background(
                color = NuvioTheme.colors.SurfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = NuvioTheme.extendedColors.textSecondary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = NuvioTheme.colors.TextPrimary
            )
        }
    }
}

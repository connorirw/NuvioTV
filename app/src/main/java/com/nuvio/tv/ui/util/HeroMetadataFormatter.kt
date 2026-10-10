package com.nuvio.tv.ui.util

import com.nuvio.tv.core.util.parseRuntimeMinutes
import java.util.Locale

fun formatHeroRuntime(runtime: String?): String? {
    val normalized = runtime?.trim()?.lowercase()?.takeIf { it.isNotBlank() } ?: return null
    val totalMinutes = parseRuntimeMinutes(normalized) ?: return runtime
    // TMDB answers with runtime 0, not null, for a title whose length it does not know yet --
    // common for anything newly added. Formatting that produced a literal "0m" next to the
    // genre. No runtime is the honest reading of zero, and callers already drop a null.
    if (totalMinutes <= 0) return null

    val wholeHours = totalMinutes / 60
    val remainingMinutes = totalMinutes % 60
    return when {
        wholeHours > 0 && remainingMinutes > 0 -> "${wholeHours}h ${remainingMinutes}m"
        wholeHours > 0 -> "${wholeHours}h"
        else -> "${remainingMinutes}m"
    }
}

fun formatCurrency(amount: Long?): String? {
    if (amount == null || amount <= 0L) return null
    return when {
        amount >= 1_000_000_000L -> {
            val billions = amount / 1_000_000_000.0
            val formatted = if (billions % 1.0 == 0.0) {
                String.format(Locale.US, "$%.0fB USD", billions)
            } else {
                String.format(Locale.US, "$%.1fB USD", billions)
            }
            formatted
        }
        amount >= 1_000_000L -> {
            val millions = amount / 1_000_000.0
            val formatted = if (millions % 1.0 == 0.0) {
                String.format(Locale.US, "$%.0fM USD", millions)
            } else {
                String.format(Locale.US, "$%.1fM USD", millions)
            }
            formatted
        }
        amount >= 1_000L -> {
            val thousands = amount / 1_000.0
            val formatted = if (thousands % 1.0 == 0.0) {
                String.format(Locale.US, "$%.0fK USD", thousands)
            } else {
                String.format(Locale.US, "$%.1fK USD", thousands)
            }
            formatted
        }
        else -> "$$amount USD"
    }
}

package com.nuvio.tv.ui.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class HeroMetadataFormatterTest {

    @Test
    fun `formats hours and minutes`() {
        assertEquals("2h 5m", formatHeroRuntime("125"))
        assertEquals("1h 30m", formatHeroRuntime("90 min"))
        assertEquals("45m", formatHeroRuntime("45"))
    }

    @Test
    fun `drops the minute part when a runtime is whole hours`() {
        assertEquals("2h", formatHeroRuntime("120"))
    }

    /**
     * TMDB answers with 0, not null, for a title whose length it does not know yet. That used to
     * reach the screen as a literal "0m" beside the genre.
     */
    @Test
    fun `returns null for a zero runtime`() {
        assertNull(formatHeroRuntime("0"))
        assertNull(formatHeroRuntime("0 min"))
        assertNull(formatHeroRuntime("0h 0m"))
    }

    @Test
    fun `returns null for a missing runtime`() {
        assertNull(formatHeroRuntime(null))
        assertNull(formatHeroRuntime("   "))
    }

    @Test
    fun `passes through text it cannot read as minutes`() {
        assertEquals("unknown", formatHeroRuntime("unknown"))
    }

    @Test
    fun `formats currency amounts into standard notation`() {
        assertEquals("$150M USD", formatCurrency(150_000_000L))
        assertEquals("$1.2B USD", formatCurrency(1_200_000_000L))
        assertEquals("$2B USD", formatCurrency(2_000_000_000L))
        assertEquals("$500K USD", formatCurrency(500_000L))
        assertEquals("$250 USD", formatCurrency(250L))
    }

    @Test
    fun `returns null for zero negative or null currency amounts`() {
        assertNull(formatCurrency(null))
        assertNull(formatCurrency(0L))
        assertNull(formatCurrency(-50_000L))
    }
}

package com.example.sundarua

import com.example.sundarua.test.RewardHelper
import org.junit.Assert.*
import org.junit.Test

class RewardHelperTest {

    private val helper = RewardHelper()

    // Untuk menguji fungsi canClaimReward jika coin cukup
    @Test
    fun testCanClaimReward_enough() {
        assertTrue(helper.canClaimReward(200, 100))
    }

    // Untuk menguji fungsi canClaimReward jika coin tidak cukup
    @Test
    fun testCanClaimReward_notEnough() {
        assertFalse(helper.canClaimReward(50, 100))
    }

    // Untuk menguji fungsi generateClaimCode untuk format yang benar
    @Test
    fun testGenerateClaimCode_format() {
        val code = helper.generateClaimCode()
        assertTrue(code.startsWith("CLM"))
        assertEquals(17, code.length) // CLM + 14 digit
    }

    // Untuk menguji fungsi formatClaimHistory untuk format yang benar
    @Test
    fun testFormatClaimHistory() {
        val formatted = helper.formatClaimHistory("Pensil", "CLM20250515123456")
        assertEquals("Pensil - Kode: CLM20250515123456", formatted)
    }
}
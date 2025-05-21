package com.example.sundarua

import com.example.sundarua.test.RewardHelper
import org.junit.Assert.*
import org.junit.Test

class RewardActivityUnitTest {

    private val helper = RewardHelper()

    @Test
    fun `canClaimReward returns true when coins sufficient`() {
        val result = helper.canClaimReward(500, 200)
        assertTrue(result)
    }

    @Test
    fun `canClaimReward returns false when coins insufficient`() {
        val result = helper.canClaimReward(50, 100)
        assertFalse(result)
    }

    @Test
    fun `generateClaimCode returns valid prefix`() {
        val code = helper.generateClaimCode()
        assertTrue(code.startsWith("CLM"))
        assertEquals(17, code.length) // CLM + yyyyMMddHHmmss = 3 + 14 = 17
    }

    @Test
    fun `formatClaimHistory formats correctly`() {
        val result = helper.formatClaimHistory("Buku", "CLM20240521000000")
        assertEquals("Buku - Kode: CLM20240521000000", result)
    }
}
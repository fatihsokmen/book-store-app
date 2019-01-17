package com.github.fatihsokmen.payment.sdk.cardpayment

import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.annotations.Fixture
import org.hamcrest.CoreMatchers
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class CardPaymentRequestTest {

    @Fixture
    private lateinit var fixtPaymentUrl: String
    @Fixture
    private lateinit var fixtOrderReference: String

    @Before
    fun setup() {
        FixtureAnnotations.initFixtures(this)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test builder when no payment url set`() {
        CardPaymentRequest.Builder()
            .build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test builder when no order reference set`() {
        CardPaymentRequest.Builder()
            .build()
    }

    @Test()
    fun `test builder when all set`() {
        val request = CardPaymentRequest.Builder()
            .paymentUrl(fixtPaymentUrl)
            .orderReference(fixtOrderReference)
            .build()
        assertThat(request, CoreMatchers.notNullValue())
    }
}
package com.github.fatihsokmen.payment.sdk.cardpayment

import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.annotations.Fixture
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class CardPaymentApiInteractorTest {

    @Mock
    private lateinit var mockPaymentService: CardPaymentService

    @Fixture
    private lateinit var fixtPaymentReference: String
    @Fixture
    private lateinit var fixtPan: String
    @Fixture
    private lateinit var fixtExpiry: String
    @Fixture
    private lateinit var fixtCvv: String
    @Fixture
    private lateinit var fixtCardHolder: String


    private lateinit var sut: CardPaymentApiInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        FixtureAnnotations.initFixtures(this)

        sut = CardPaymentApiInteractor(mockPaymentService)
    }

    @Test
    fun `authorize payment`() = runBlocking {
        runBlocking {
            sut.authorizePayment(fixtPaymentReference, fixtPan, fixtExpiry, fixtCvv, fixtCardHolder)

            verify(mockPaymentService).authorizePayment(
                fixtPaymentReference,
                fixtPan,
                fixtExpiry,
                fixtCvv,
                fixtCardHolder
            )
            Unit
        }
    }
}
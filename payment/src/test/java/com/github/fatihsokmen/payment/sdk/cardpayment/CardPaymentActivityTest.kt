package com.github.fatihsokmen.payment.sdk.cardpayment

import android.app.Activity
import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.annotations.Fixture
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowActivity

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class CardPaymentActivityTest {

    @Fixture
    private lateinit var fixtPaymentUrl: String
    @Fixture
    private lateinit var fixtOrderReference: String
    @Fixture
    private lateinit var fixtErrorMessage: String

    private lateinit var sut: CardPaymentActivity
    private lateinit var shadowSut: ShadowActivity

    @Before
    fun setUp() {
        FixtureAnnotations.initFixtures(this)
        val intent = CardPaymentActivity.getIntent(
            context = RuntimeEnvironment.application.applicationContext,
            paymentUrl = fixtPaymentUrl,
            merchantOrderReference = fixtOrderReference
        )
        sut = Robolectric.buildActivity(CardPaymentActivity::class.java, intent).setup().get()
        shadowSut = Shadows.shadowOf(sut)
    }

    @Test
    fun `on payment authorized`() {
        // run
        sut.onPaymentAuthorized()

        // verify
        val data = shadowSut.resultIntent.getParcelableExtra<CardPaymentData>(CardPaymentData.INTENT_DATA_KEY)
        assertEquals(data.code, CardPaymentData.STATUS_PAYMENT_AUTHORIZED)
        assertEquals(Activity.RESULT_OK, shadowSut.resultCode)
        assertTrue(sut.isFinishing)
    }

    @Test
    fun `on payment failed`() {
        // run
        sut.onPaymentFailed()

        // verify
        val data = shadowSut.resultIntent.getParcelableExtra<CardPaymentData>(CardPaymentData.INTENT_DATA_KEY)
        assertEquals(data.code, CardPaymentData.STATUS_PAYMENT_FAILED)
        assertEquals(Activity.RESULT_OK, shadowSut.resultCode)
        assertTrue(sut.isFinishing)
    }

    @Test
    fun `on generic error occurred`() {
        // run
        sut.onGenericError(fixtErrorMessage)

        // verify
        val data = shadowSut.resultIntent.getParcelableExtra<CardPaymentData>(CardPaymentData.INTENT_DATA_KEY)
        assertEquals(data.code, CardPaymentData.STATUS_GENERIC_ERROR)
        assertEquals(data.reason, fixtErrorMessage)
        assertEquals(Activity.RESULT_OK, shadowSut.resultCode)
        assertTrue(sut.isFinishing)
    }
}
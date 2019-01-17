package com.github.fatihsokmen.payment.sdk.cardpayment

import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.JFixture
import com.flextrade.jfixture.annotations.Fixture
import com.github.fatihsokmen.payment.sdk.TestCoroutinesDispatcherProvider
import com.github.fatihsokmen.payment.sdk.cardpayment.resources.StringResources
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CardPaymentPresenterTest {

    @Mock
    private lateinit var mockView: CardPaymentContract.View
    @Mock
    private lateinit var mockInteractions: CardPaymentContract.Interactions
    @Mock
    private lateinit var mockCardPaymentApiInteractor: CardPaymentApiInteractor
    @Mock
    private lateinit var mockStringResources: StringResources

    @Fixture
    private lateinit var fixtPaymentUrl: String
    @Fixture
    private lateinit var fixtPaymentReference: String

    private lateinit var sut: CardPaymentPresenter
    private val fixture = JFixture()

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        FixtureAnnotations.initFixtures(this)

        sut = CardPaymentPresenter(
            fixtPaymentUrl,
            fixtPaymentReference,
            mockView,
            mockInteractions,
            mockCardPaymentApiInteractor,
            mockStringResources,
            TestCoroutinesDispatcherProvider()
        )
    }

    @Test
    fun `on card number focus gained`() {
        val fixtLabel = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.CARD_NUMBER_LABEL_RESOURCE)).thenReturn(fixtLabel)

        sut.onCardNumberFocusGained()

        verify(mockView).setFloatingHintText(fixtLabel)
    }

    @Test
    fun `on expiry focus gained`() {
        val fixtLabel = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.CARD_EXPIRY_LABEL_RESOURCE)).thenReturn(fixtLabel)

        sut.onExpireDateFocusGained()

        verify(mockView).setFloatingHintText(fixtLabel)
    }

    @Test
    fun `on cvv focus gained`() {
        val fixtLabel = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.CVV_LABEL_RESOURCE)).thenReturn(fixtLabel)

        sut.onCvvFocusGained()

        verify(mockView).setFloatingHintText(fixtLabel)
    }

    @Test
    fun `click pay button and show error when pan empty`(): Unit = runBlocking {
        // init
        whenever(mockView.pan).thenReturn("")
        val fixtPanError = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.ERROR_EMPTY_PAN_RESOURCE))
            .thenReturn(fixtPanError)

        // run
        sut.onPayClicked()

        //verify
        verify(mockView).showError(fixtPanError)
    }

    @Test
    fun `click pay button and show error when pan not complete`(): Unit = runBlocking {
        // init
        whenever(mockView.pan).thenReturn(fixture.create(String::class.java).substring(0,1))
        val fixtPanError = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.ERROR_EMPTY_PAN_RESOURCE))
            .thenReturn(fixtPanError)

        // run
        sut.onPayClicked()

        //verify
        verify(mockView).showError(fixtPanError)
    }

    @Test
    fun `click pay button and show error when expiry empty`(): Unit = runBlocking {
        // init
        whenever(mockView.pan).thenReturn(VALID_PAN)
        whenever(mockView.expiry).thenReturn("")
        val fixtExpiryError = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.ERROR_EMPTY_EXPIRY_RESOURCE))
            .thenReturn(fixtExpiryError)

        // run
        sut.onPayClicked()

        // verify
        verify(mockView).showError(fixtExpiryError)
    }

    @Test
    fun `click pay button and show error when expiry not complete`(): Unit = runBlocking {
        // init
        whenever(mockView.pan).thenReturn(VALID_PAN)
        whenever(mockView.expiry).thenReturn(fixture.create(String::class.java).substring(0,1))
        val fixtExpiryError = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.ERROR_EMPTY_EXPIRY_RESOURCE))
            .thenReturn(fixtExpiryError)

        // run
        sut.onPayClicked()

        // verify
        verify(mockView).showError(fixtExpiryError)
    }

    @Test
    fun `click pay button and show error when cvv empty`(): Unit = runBlocking {
        // init
        whenever(mockView.pan).thenReturn(VALID_PAN)
        whenever(mockView.expiry).thenReturn(VALID_EXPIRY)
        whenever(mockView.cvv).thenReturn("")
        val fixtCvvError = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.ERROR_EMPTY_CVV_RESOURCE))
            .thenReturn(fixtCvvError)

        // run
        sut.onPayClicked()

        // verify
        verify(mockView).showError(fixtCvvError)
    }

    @Test
    fun `click pay button and show error when cvv not complete`(): Unit = runBlocking {
        // init
        whenever(mockView.pan).thenReturn(VALID_PAN)
        whenever(mockView.expiry).thenReturn(VALID_EXPIRY)
        whenever(mockView.cvv).thenReturn(fixture.create(String::class.java).substring(0,1))
        val fixtCvvError = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.ERROR_EMPTY_CVV_RESOURCE))
            .thenReturn(fixtCvvError)

        // run
        sut.onPayClicked()

        // verify
        verify(mockView).showError(fixtCvvError)
    }

    @Test
    fun `click pay button and show error when cardholder empty`(): Unit = runBlocking {
        // init
        whenever(mockView.pan).thenReturn(VALID_PAN)
        whenever(mockView.expiry).thenReturn(VALID_EXPIRY)
        whenever(mockView.cvv).thenReturn(VALID_CVV)
        whenever(mockView.cardHolder).thenReturn("")
        val fixtCardHolderError = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.ERROR_EMPTY_CARDHOLDER_RESOURCE))
            .thenReturn(fixtCardHolderError)

        // run
        sut.onPayClicked()

        // verify
        verify(mockView).showError(fixtCardHolderError)
    }

    @Test
    fun `click pay when all fields set`(): Unit = runBlocking {
        // init
        val fixtProgressMessage = fixture.create(String::class.java)
        whenever(mockStringResources.getString(CardPaymentPresenter.PROGRESS_MESSAGE_RESOURCE))
            .thenReturn(fixtProgressMessage)
        whenever(mockView.pan).thenReturn(VALID_PAN)
        whenever(mockView.expiry).thenReturn(VALID_EXPIRY)
        whenever(mockView.cvv).thenReturn(VALID_CVV)
        val fixtCardHolder = fixture.create(String::class.java)
        whenever(mockView.cardHolder).thenReturn(fixtCardHolder)
        val fixtResponse = fixture.create(String::class.java)
        whenever(
            mockCardPaymentApiInteractor.authorizePayment(
                fixtPaymentReference,
                VALID_PAN,
                VALID_EXPIRY,
                VALID_CVV,
                fixtCardHolder
            )
        ).thenReturn(fixtResponse)

        // run
        sut.onPayClicked()

        // verify
        val inOrder = inOrder(mockView, mockCardPaymentApiInteractor)
        inOrder.verify(mockView).showProgress(true, fixtProgressMessage)
        inOrder.verify(mockCardPaymentApiInteractor).authorizePayment(
            fixtPaymentReference,
            VALID_PAN,
            VALID_EXPIRY,
            VALID_CVV,
            fixtCardHolder
        )
        inOrder.verify(mockView).showProgress(false)
        Unit
    }

    companion object {
        private const val VALID_PAN = "4111111111111111"
        private const val VALID_CVV = "123"
        private const val VALID_EXPIRY = "01/19"
    }
}
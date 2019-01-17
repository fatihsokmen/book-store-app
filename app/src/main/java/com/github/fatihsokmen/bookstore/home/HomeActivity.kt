package com.github.fatihsokmen.bookstore.home


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.github.fatihsokmen.bookstore.App
import com.github.fatihsokmen.bookstore.R
import com.github.fatihsokmen.bookstore.basket.BasketFragment
import com.github.fatihsokmen.bookstore.products.ProductsFragment
import com.github.fatihsokmen.bookstore.orders.OrdersFragment
import com.github.fatihsokmen.payment.sdk.cardpayment.CardPaymentData
import javax.inject.Inject

class HomeActivity : AppCompatActivity(),
    HomeActivityContract.View, OnNavigationItemSelectedListener {

    @Inject
    lateinit var presenter: HomeActivityContract.Presenter

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.navigation)
    lateinit var navigation: BottomNavigationView

    private lateinit var badge: ViewGroup
    private lateinit var badgeContent: TextView

    private var lastSelectedItemId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(this)

        if (intent.getIntExtra("tab", 0) != 0) {
            navigation.selectedItemId = intent.getIntExtra("tab", 0)
        } else if (savedInstanceState == null) {
            navigation.selectedItemId = R.id.navigate_basket
        }
        addBadgeView()

        createComponent(this).inject(this)

        presenter.init()
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        val itemId = menu.itemId
        if (itemId != lastSelectedItemId) {
            changeFragment(itemId)
            lastSelectedItemId = itemId
        }
        return true
    }

    override fun basketSizeChanged(size: Int) {
        if (size > 0) {
            badge.visibility = View.VISIBLE
            badgeContent.text = "$size"
        } else {
            badge.visibility = View.GONE
        }
    }


    private fun addBadgeView() {
        val bottomMenu = navigation.getChildAt(0) as? BottomNavigationMenuView
        val basketItemView = bottomMenu?.getChildAt(1) as? BottomNavigationItemView

        badge = LayoutInflater.from(this)
            .inflate(R.layout.view_navigation_badge, bottomMenu, false) as ViewGroup
        basketItemView?.addView(badge)
        badgeContent = badge.getChildAt(0) as TextView
    }

    private fun changeFragment(itemId: Int) {
        val arguments = Bundle()
        val fragment = when (itemId) {
            R.id.navigate_books -> {
                setTitle(R.string.fragment_title_books)
                ProductsFragment()
            }
            R.id.navigate_basket -> {
                setTitle(R.string.fragment_title_basket)
                BasketFragment()
            }
            R.id.navigate_orders -> {
                setTitle(R.string.fragment_title_orders)
                OrdersFragment()
            }
            else -> throw IllegalArgumentException("Id is not supported")
        }
        fragment.arguments = arguments

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_content, fragment, itemId.toString())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cleanup()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BasketFragment.CARD_PAYMENT_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> basketFragment?.onCardPaymentResponse(
                    CardPaymentData.getFromIntent(data!!)
                )
                Activity.RESULT_CANCELED -> basketFragment?.onCardPaymentCancelled()
            }
        }
    }

    private val basketFragment: BasketFragment?
        get() = supportFragmentManager.fragments.firstOrNull { it is BasketFragment }?.let {
            it as BasketFragment
        }

    companion object {
        private fun createComponent(activity: HomeActivity)
                : HomeActivityComponent {
            val baseComponent = (activity.application as App).baseComponent
            return DaggerHomeActivityComponent
                .builder()
                .view(activity)
                .baseComponent(baseComponent)
                .build()
        }
    }
}

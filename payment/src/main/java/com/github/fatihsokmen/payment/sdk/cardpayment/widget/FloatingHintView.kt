package com.github.fatihsokmen.payment.sdk.cardpayment.widget


import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.LayoutDirection
import android.view.View
import android.widget.TextView
import com.github.fatihsokmen.payment.sdk.R

internal class FloatingHintView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttrs: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttrs) {

    private val hint: TextView = TextView(context).apply {
        setTextColor(ContextCompat.getColor(context, R.color.blue))
    }

    var text: String? = null
        set(value) {
            hint.text = value
        }

    init {
        addView(hint)
        layoutDirection = LayoutDirection.LTR
    }

    fun animateToAlignViewStart(v: View) {
        x = v.x
    }
}
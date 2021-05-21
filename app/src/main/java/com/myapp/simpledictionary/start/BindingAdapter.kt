package com.myapp.simpledictionary.start

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun bindSpinner(view: View, visibilitySpinner: Boolean) {
    view.visibility = if (visibilitySpinner) View.VISIBLE else View.GONE
}

@BindingAdapter("android:visibility")
fun bindCheckBox(view: View, visibilityCheckBox: Boolean) {
    view.visibility = if (visibilityCheckBox) View.VISIBLE else View.GONE
}
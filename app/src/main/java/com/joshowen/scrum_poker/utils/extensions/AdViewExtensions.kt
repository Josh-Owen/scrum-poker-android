package com.joshowen.scrum_poker.utils.extensions

import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

fun AdView.loadAdvert() {
    this.visibility = View.VISIBLE
    this.loadAd(AdRequest.Builder().build())
}

fun AdView.unLoadAdvert() {
    this.destroy()
    this.visibility = View.GONE
}

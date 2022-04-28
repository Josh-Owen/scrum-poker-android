package com.joshowen.scrum_poker.utils.extensions

import android.view.View
import io.reactivex.rxjava3.core.Observable

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.onClick(): Observable<Unit> {
    return Observable.create { emitter ->
        this.setOnClickListener {
            emitter.onNext(Unit)
        }
    }
}
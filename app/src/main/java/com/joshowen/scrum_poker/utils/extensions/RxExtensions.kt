package com.joshowen.scrum_poker.utils.extensions

import android.view.View
import io.reactivex.rxjava3.core.Observable

fun View.onClick(): Observable<Unit> {
    return Observable.create { emitter ->
        this.setOnClickListener {
            emitter.onNext(Unit)
        }
    }
}
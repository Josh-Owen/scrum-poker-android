package com.joshowen.scrum_poker.utils

import android.view.View
import io.reactivex.rxjava3.core.Observable

class RxExtensions {

    companion object {
        fun View.onClick(): Observable<Unit> {
            return Observable.create { emitter ->
                this.setOnClickListener {
                    emitter.onNext(Unit)
                }
            }
        }
    }
}
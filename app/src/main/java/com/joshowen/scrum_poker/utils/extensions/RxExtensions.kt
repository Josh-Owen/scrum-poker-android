package com.joshowen.scrum_poker.utils

import android.view.View
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction


fun View.onClick(): Observable<Unit> {
    return Observable.create { emitter ->
        this.setOnClickListener {
            emitter.onNext(Unit)
        }
    }
}

fun <T, O1> Observable<T>.takeWhen(toListen: Observable<O1>): Observable<T> {
    return toListen.withLatestFrom(this) { _, T1 -> T1 }
}
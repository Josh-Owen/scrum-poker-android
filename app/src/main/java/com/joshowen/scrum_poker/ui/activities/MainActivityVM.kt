package com.joshowen.scrum_poker.ui.activities

import android.app.Application
import com.joshowen.scrum_poker.ApplicationConfiguration.Companion.ADVERTISEMENT_START_UP_DELAY
import com.joshowen.scrum_poker.base.BaseViewModel
import com.joshowen.scrum_poker.utils.wrappers.PreferenceManagerWrapper.Companion.getIsAdvertisementsEnabled
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface Inputs {

}

interface Outputs {
    fun loadAdvert(): Observable<Boolean>
}

@HiltViewModel
class MainActivityVM @Inject constructor(application: Application) : BaseViewModel(application),
    Inputs, Outputs {

    //region Variables

    val inputs: Inputs = this
    val outputs: Outputs = this

    private val obsLoadAdvertisementDelay: Observable<Boolean> =
        Observable.timer(ADVERTISEMENT_START_UP_DELAY, TimeUnit.SECONDS)
            .flatMap {
                Observable.create { emitter ->
                    emitter.onNext(getIsAdvertisementsEnabled(application, true))
                }
            }

    //endregion


    //endregion

    //region Inputs

    //endregion

    //region Output
    override fun loadAdvert(): Observable<Boolean> {
        return obsLoadAdvertisementDelay
    }
    //endregion
}
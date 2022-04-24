package com.joshowen.firebaserepository.repositories

import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyRepositoryImpl @Inject constructor(): LobbyRepository {

    override fun getLobbyMemberCount(): Observable<Int> {
        return Observable.just(8)
    }
}
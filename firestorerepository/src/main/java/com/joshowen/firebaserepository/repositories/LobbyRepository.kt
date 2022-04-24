package com.joshowen.firebaserepository.repositories

import io.reactivex.rxjava3.core.Observable


interface LobbyRepository {
    fun getLobbyMemberCount() : Observable<Int>
}
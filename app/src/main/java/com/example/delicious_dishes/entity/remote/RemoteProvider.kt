package com.example.delicious_dishes.entity.remote

interface RemoteProvider {
    fun provideRemote(): TmdbApi
}
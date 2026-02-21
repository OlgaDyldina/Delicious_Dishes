package com.example.delicious_dishes.di.modules

import android.content.Context
import com.example.delicious_dishes.repository.MainRepository
import com.example.delicious_dishes.domain.Interactor
import com.example.delicious_dishes.entity.remote.TmdbApi
import com.example.delicious_dishes.preference.PreferenceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule (val context: Context){
    @Provides
    fun provideContext() = context
    @Singleton
    @Provides
    fun providePreferences(context: Context) = PreferenceProvider(context)
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi, preferenceProvider: PreferenceProvider) = Interactor(repo = repository, retrofitService = tmdbApi, preferences = preferenceProvider)
}
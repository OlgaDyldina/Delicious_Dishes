package com.example.delicious_dishes.di

import com.example.delicious_dishes.di.modules.DatabaseModule
import com.example.delicious_dishes.di.modules.DomainModule
import com.example.delicious_dishes.entity.remote.RemoteProvider
import com.example.delicious_dishes.viewmodel.RecipeViewModel
import com.example.delicious_dishes.viewmodel.SettingsFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [RemoteProvider::class],
    modules = [
        com.example.delicious_dishes.entity.remote.RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    fun inject(homeFragmentViewModel: RecipeViewModel)
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}
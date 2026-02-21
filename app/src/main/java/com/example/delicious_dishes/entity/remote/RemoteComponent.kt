package com.example.delicious_dishes.entity.remote

import androidx.test.espresso.core.internal.deps.dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RemoteModule::class]
)
interface RemoteComponent : RemoteProvider
package com.davidbronn.personalimdb.di

import com.davidbronn.personalimdb.repository.landing.PreferenceHelper
import com.davidbronn.personalimdb.repository.landing.PreferenceHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created by Jude on 17/June/2020
 */

@InstallIn(ApplicationComponent::class)
@Module
abstract class ProvidersModule {

    @Binds
    abstract fun providePreferencesHelper(prefs: PreferenceHelperImpl): PreferenceHelper
}
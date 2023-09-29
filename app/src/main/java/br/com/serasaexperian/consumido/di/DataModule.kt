package br.com.serasaexperian.consumido.di

import android.content.Context
import br.com.serasaexperian.consumido.data.CandyJockerStorageImpl
import br.com.serasaexperian.consumido.data.RProviderImpl
import br.com.serasaexperian.consumido.domain.AppVersionSaver
import br.com.serasaexperian.consumido.domain.CandyJockerStorage
import br.com.serasaexperian.consumido.domain.GeneralDataManager
import br.com.serasaexperian.consumido.domain.RProvider
import br.com.serasaexperian.consumido.domain.TimesIncreaser
import br.com.serasaexperian.consumido.viewmodels.CandyJockerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideGeneralDataManager(@ApplicationContext context: Context) : GeneralDataManager{
        return GeneralDataManager(provideRProvider(context))
    }
    @Provides
    @Singleton
    fun provideRProvider(@ApplicationContext context: Context) : RProvider{
        return RProviderImpl(context)
    }

    @Provides
    @Singleton
    fun provideCandyJockerViewModel(@ApplicationContext context: Context) : CandyJockerViewModel{
        return CandyJockerViewModel(provideCandyJockerStorage(context))
    }

    @Provides
    @Singleton
    fun provideTimesIncreaser(@ApplicationContext context: Context) : TimesIncreaser{
        return TimesIncreaser(provideCandyJockerStorage(context))
    }

    @Provides
    @Singleton
    fun provideAppVersionSaver(@ApplicationContext context: Context) : AppVersionSaver{
        return AppVersionSaver(provideCandyJockerStorage(context))
    }

    @Provides
    @Singleton
    fun provideCandyJockerStorage(@ApplicationContext context: Context) : CandyJockerStorage {
        return CandyJockerStorageImpl(context)
    }
}
package br.com.serasaexperian.consumido.di

import android.content.Context
import br.com.serasaexperian.consumido.data.CandyJockerStorageImpl
import br.com.serasaexperian.consumido.domain.CandyJockerStorage
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
    fun provideCandyJockerStorage(@ApplicationContext context: Context) : CandyJockerStorage {
        return CandyJockerStorageImpl(context)
    }

}
package br.com.serasaexperian.consumido.di

import android.content.Context
import br.com.serasaexperian.consumido.data.SImpl
import br.com.serasaexperian.consumido.data.CImpl
import br.com.serasaexperian.consumido.data.RImpl
import br.com.serasaexperian.consumido.domain.AppVersionSaver
import br.com.serasaexperian.consumido.domain.S
import br.com.serasaexperian.consumido.domain.ConnectionChecker
import br.com.serasaexperian.consumido.domain.GDM
import br.com.serasaexperian.consumido.domain.RP
import br.com.serasaexperian.consumido.domain.TI
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
    fun mklcdmkld(@ApplicationContext context: Context) : GDM{
        return GDM(njscksdjk(context))
    }
    @Provides
    @Singleton
    fun njscksdjk(@ApplicationContext context: Context) : RP{
        return RImpl(context)
    }

    @Provides
    @Singleton
    fun ndjknjsdkssss(@ApplicationContext context: Context) : CandyJockerViewModel{
        return CandyJockerViewModel(mcdkkGgg(context))
    }

    @Provides
    @Singleton
    fun dnjknjkFFaa(@ApplicationContext context: Context) : TI{
        return TI(mcdkkGgg(context))
    }

    @Provides
    @Singleton
    fun ncjdnjkdcjnkT66(@ApplicationContext context: Context) : AppVersionSaver{
        return AppVersionSaver(mcdkkGgg(context))
    }

    @Provides
    @Singleton
    fun mcdkkGgg(@ApplicationContext context: Context) : S {
        return SImpl(context)
    }

    @Provides
    @Singleton
    fun pcCh() : ConnectionChecker {
        return CImpl()
    }

}
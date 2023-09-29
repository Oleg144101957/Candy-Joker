package br.com.serasaexperian.consumido.domain

import javax.inject.Inject

class GeneralDataManager @Inject constructor(private val rProvider: RProvider) {
    suspend fun takeData() : String ? {
        return rProvider.provideData()
    }
}
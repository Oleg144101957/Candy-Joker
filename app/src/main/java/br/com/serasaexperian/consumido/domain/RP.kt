package br.com.serasaexperian.consumido.domain

interface RP {
    suspend fun provideData(): String?
}
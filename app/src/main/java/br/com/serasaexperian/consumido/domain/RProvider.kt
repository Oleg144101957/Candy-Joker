package br.com.serasaexperian.consumido.domain

interface RProvider {
    suspend fun provideData(): String?
}
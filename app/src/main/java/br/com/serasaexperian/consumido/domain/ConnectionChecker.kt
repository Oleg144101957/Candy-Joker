package br.com.serasaexperian.consumido.domain

import android.content.Context

interface ConnectionChecker {
    fun isConnectionExist(context: Context) : Boolean

}
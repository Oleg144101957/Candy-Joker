package br.com.serasaexperian.consumido.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import br.com.serasaexperian.consumido.domain.ConnectionChecker

class CImpl : ConnectionChecker {
    override fun isConnectionExist(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}
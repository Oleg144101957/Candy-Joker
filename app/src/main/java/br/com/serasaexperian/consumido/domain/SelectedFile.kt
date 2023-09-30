package br.com.serasaexperian.consumido.domain

import android.net.Uri
import android.webkit.ValueCallback

interface SelectedFile {
    fun onSelectImages(selectedImages: ValueCallback<Array<Uri?>>)

}
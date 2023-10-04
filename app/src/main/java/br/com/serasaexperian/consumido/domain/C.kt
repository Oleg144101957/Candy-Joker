package br.com.serasaexperian.consumido.domain

data class C(
    val id: Int,
    val image: Int,
    val isVisible: Boolean = true,
    val description: String = "element"
)
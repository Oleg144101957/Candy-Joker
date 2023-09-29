package br.com.serasaexperian.consumido.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import br.com.serasaexperian.consumido.R

sealed class PanelsRoutes(val direction: String){
    object ProcessingPanelRoute : PanelsRoutes("ProcessingPanelRoute")
    object VisualMenuPanelRoute : PanelsRoutes("VisualMenuPanelRoute")
    object VisualPlaygroundPanelRoute : PanelsRoutes("VisualPlaygroundPanelRoute")
    object VisualSettingsPanelRoute : PanelsRoutes("VisualSettingsPanelRoute")

    companion object{
        val candyFont = FontFamily(Font(R.font.candy_font))

        val packageM = "g6wxsu"
        val referer = "58youwbs6"
        val gaid = "86820g1a4"
        val appVer = "2a1p9elcp"
        val osVer = "8vcqfzlm8"
        val timeStamp = "cq2fk8xh"
        val agent = "8q06aunp"
    }
}

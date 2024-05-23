package com.ukropsoft.datastore

import com.ukropsoft.datastore.ui.theme.Pink
import kotlinx.serialization.Serializable

@Serializable
data class SettingsData(
    val textSize: Int = 40,
    val bgColor: ULong = Pink.value,
    val selectedOption: Options = Options.OPTION_1
)
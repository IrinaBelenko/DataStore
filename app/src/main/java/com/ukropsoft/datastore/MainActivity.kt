package com.ukropsoft.datastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ukropsoft.datastore.ui.theme.Blue
import com.ukropsoft.datastore.ui.theme.DataStoreTheme
import com.ukropsoft.datastore.ui.theme.Pink
import com.ukropsoft.datastore.ui.theme.Red
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager = ProtoDataStoreManager(this)
        setContent {
            DataStoreTheme {

                val settingsState = dataStoreManager
                    .getSettings()
                    .collectAsState(initial = SettingsData())


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(settingsState.value.bgColor)
                ) {
                    MainScreen(dataStoreManager, settingsState)
                }
            }
        }
    }
}

@Composable
fun MainScreen(dataStoreManager: ProtoDataStoreManager, settingsState: State<SettingsData>) {
    val coroutine = rememberCoroutineScope()
    var selectedOption by remember { mutableStateOf<Options?>(null) }
    val options = Options.entries.toTypedArray()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(0.5f)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically)
        ) {
            Text(
                text = "Some text",
                color = Color.White,
                fontSize = settingsState.value.textSize.sp
            )
        }
        Button(onClick = {
            coroutine.launch {
                dataStoreManager.saveSettings(
                    SettingsData(15, Blue.value)
                )
            }
        }) {
            Text(text = "Blue")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            coroutine.launch {
                dataStoreManager.saveSettings(
                    SettingsData(25, Red.value)
                )
            }
        }) {
            Text(text = "Red")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            coroutine.launch {
                dataStoreManager.saveSettings(
                    SettingsData(35, Pink.value)
                )
            }
        }) {
            Text(text = "Pink")
        }
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = option.displayName,
                    modifier = Modifier.padding(start = 8.dp)
                )
                RadioButton(
                    selected = settingsState.value.selectedOption == option,
                    onClick = {
                        selectedOption = option
                        coroutine.launch {
                            dataStoreManager.saveSelectedOption(option)
                        }
                    }
                )
            }
        }

        // Optionally, display the selected option
        Text(
            text = "Selected option: ${selectedOption?.displayName ?: "None"}",
            modifier = Modifier.padding(top = 16.dp)
        )

    }
}


package omni.oober

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import omni.oober.ui.theme.OoberTheme
import java.util.*

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private var buttonEnabled = false
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)
        setContent {
            OoberTheme {
                val initText = "Oob me!"
                val text = remember { mutableStateOf(initText) }
                val oober = remember { mutableStateOf("oob") }
                val ooberText = remember { mutableStateOf(oober(initText, oober)) }
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Oober!",
                            fontSize = 64.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                        )
                        OutlinedTextField(
                            value = text.value,
                            onValueChange = { text.value = it; ooberText.value = oober(it, oober) },
                            label = { Text("Ooberfy me!") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        )
                        OutlinedTextField(
                            value = ooberText.value,
                            onValueChange = { ooberText.value = it },
                            readOnly = true,
                            label = { Text("Ooberfied") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        )
                        OutlinedButton(
                            onClick = { if (buttonEnabled) speakOut(ooberText.value) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                        ) {
                            Text(text = "Oob me.", fontSize = 32.sp)
                        }
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Omnieboer",
                                textAlign = TextAlign.Center,
                                color = Color.Gray,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
        }
        buttonEnabled = true
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}

@ExperimentalStdlibApi
fun oober(text: String, replacement: MutableState<String>): String {
    var res = ""
    for (char in text) {
        when {
            "aoeiu".contains(char) -> {
                res += replacement.value.lowercase()
            }
            "AOEIU".contains(char) -> {
                res += replacement.value.capitalize(Locale.ROOT)
            }
            else -> {
                res += char
            }
        }
    }
    return res
}
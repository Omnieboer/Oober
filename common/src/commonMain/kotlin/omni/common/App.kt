package omni.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

@Composable
fun App() {
    val initText = "Oob me!"
    var text by remember { mutableStateOf(initText) }
    var ooberText by remember { mutableStateOf(ooberfy(initText)) }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(value = text,
            onValueChange = { text = it; ooberText = ooberfy(it) },
            label = { Text("Ooberfy me!") },
        )
        OutlinedTextField(value = ooberText,
            onValueChange = { ooberText = it },
            readOnly = true,
            label = { Text("Ooberfied") })
    }
}

fun ooberfy(text: String): String {
    var res = ""
    for (char in text) {
        when {
            "aoeiu".contains(char) -> {
                res += "oob"
            }
            "AOEIU".contains(char) -> {
                res += "Oob"
            }
            else -> {
                res += char
            }
        }
    }
    return res
}

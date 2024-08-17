package pk.aywalk.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pk.aywalk.R

@Composable
fun SearchInput()
{
    Column (modifier = Modifier
        .fillMaxWidth(fraction = 0.9F)) {
        Input()
    }
}

@Composable
fun Input() {
    Column(modifier = Modifier.fillMaxWidth()) {
        var from = ""
        Row() {
            StationInputField(modifier = Modifier, role = "From", output = { text -> from = text })
            FloatingActionButton(modifier = Modifier.size(55.dp).padding(start = 12.dp),
                onClick = {
                    // TODO ask for location, disable text input, use location
                },
                containerColor = Color.White) {
                Icon(modifier = Modifier.size(30.dp),
                    imageVector = Icons.Outlined.LocationOn,
                    tint = Color.Black,
                    contentDescription = "Use current location")
            }
        }
        var to = ""
        StationInputField(role = "To", output = { text -> to = text })
        StartSearchButton {

        }
    }
}

@Composable
fun StationInputField(modifier: Modifier = Modifier, role: String, output : (text: String) -> Unit) {
    var text by remember { mutableStateOf("") }

    TextField(value = text, onValueChange = { text = it; output(text) }, label = { Text(role) })
}

@Composable
fun StartSearchButton(onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(),
           horizontalAlignment = Alignment.CenterHorizontally)
    {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray
            )
        ) {
            Text("Search")
        }
    }
}


@Composable
@Preview
fun PreviewSearchInput()
{
    SearchInput()
}
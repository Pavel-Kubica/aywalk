package pk.aywalk.search

import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.Locale

@Composable
fun SearchInput(locClient: FusedLocationProviderClient, onSubmit: () -> Unit)
{
    Column (modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(fraction = 0.9F)) {
        Input(locClient)
        StartSearchButton (onClick = onSubmit)
    }
}

@Composable
fun Input(locClient: FusedLocationProviderClient) {
    Column(modifier = Modifier.fillMaxWidth()) {
        var from by remember { mutableStateOf("") }
        Row {
            TextField(value = from, onValueChange = { from = it }, label = { Text("From") })
            LocationButton(locClient = locClient, store = {loc -> from = loc})
        }
        var to by remember { mutableStateOf("") }
        TextField(value = to, onValueChange = { to = it }, label = { Text("To") })


    }
}

@Composable
fun LocationButton(locClient: FusedLocationProviderClient, modifier: Modifier = Modifier, store : (location: String) -> Unit)
{
    var isClicked by remember { mutableStateOf(false) }
    FloatingActionButton(modifier = modifier.size(55.dp).padding(start = 12.dp),
        onClick = {
            isClicked = true
        },
        containerColor = Color.White) {
        Icon(modifier = Modifier.fillMaxSize(0.8f),
            imageVector = Icons.Outlined.LocationOn,
            tint = Color.Black,
            contentDescription = "Use current location")
    }
    if (isClicked)
    {
        isClicked = false
        if (ActivityCompat.checkSelfPermission(
                LocalContext.current as Activity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                LocalContext.current as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION), 0)
            return
        }
        locClient.lastLocation.addOnSuccessListener { location ->
            store(String.format(Locale.getDefault(), "%.6f,%.6f",location.latitude, location.longitude))
        }
    }
}

@Composable
fun StartSearchButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray
        )
    ) {
        Text("Search")
    }
}


//@Composable
//@Preview
//fun PreviewSearchInput()
//{
//    Input()
//}

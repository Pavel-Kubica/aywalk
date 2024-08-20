package pk.aywalk.ui.screen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.navigation.NavController
import com.commandiron.wheel_picker_compose.WheelDateTimePicker
import com.google.android.gms.location.LocationServices
import pk.aywalk.Screens
import java.util.Locale

@Composable
fun SearchInput(navController: NavController)
{
    Column (modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(fraction = 0.9F)) {
        var from = ""
        var to = ""
        var dateTime: java.time.LocalDateTime? = null
        Input(onFromChange = { newFrom -> from = newFrom }, onToChange = {newTo -> to = newTo}, onTimeChange = {newTime -> dateTime = newTime})
        StartSearchButton(onClick = { navController.navigate(route = Screens.ResultsScreen.route + "/from=${from}&to=${to}&time=${dateTime}") } )
    }
}

@Composable
fun Input(onFromChange: (newFrom: String) -> Unit, onToChange: (newTo: String) -> Unit, onTimeChange: (newTime: java.time.LocalDateTime) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        var from by remember { mutableStateOf("") }
        Row {
            TextField(value = from, onValueChange = { from = it; onFromChange(it) }, label = { Text("From") })
            LocationButton(store = {loc -> from = loc})
        }
        var to by remember { mutableStateOf("") }
        TextField(value = to, onValueChange = { to = it; onToChange(it) }, label = { Text("To") })
        WheelDateTimePicker(onSnappedDateTime = onTimeChange, yearsRange = IntRange(2024, 2024))
    }
}

@Composable
fun LocationButton(modifier: Modifier = Modifier, store : (location: String) -> Unit)
{
    val activity = LocalContext.current as Activity
    val locClient = LocationServices.getFusedLocationProviderClient(activity)
    IconButton(modifier = modifier
        .size(55.dp)
        .padding(start = 12.dp),
        onClick = {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION), 0)
            }
            locClient.lastLocation.addOnSuccessListener { location ->
                store(String.format(Locale.getDefault(), "%.6f,%.6f",location.latitude, location.longitude))
            }
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.White
        )) {
        Icon(modifier = Modifier.fillMaxSize(0.8f),
            imageVector = Icons.Outlined.LocationOn,
            tint = Color.Black,
            contentDescription = "Use current location")
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
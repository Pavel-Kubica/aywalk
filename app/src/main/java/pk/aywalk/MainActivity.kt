package pk.aywalk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import pk.aywalk.search.SearchInput
import pk.aywalk.ui.theme.AYWALKTheme

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            AYWALKTheme {
                SearchInput(fusedLocationClient, onSubmit = {
                    // TODO get from and to from SearchInput, class to transform it, start SearchResultsActivity
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}


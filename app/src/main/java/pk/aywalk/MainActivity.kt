package pk.aywalk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pk.aywalk.model.ConnectionFinder
import pk.aywalk.ui.theme.AYWALKTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                ConnectionFinder.buildStopData()
            }
        }
        setContent {
            AYWALKTheme {
                Navigation()
            }
        }
    }
}


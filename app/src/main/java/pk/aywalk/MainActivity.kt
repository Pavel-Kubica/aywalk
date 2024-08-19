package pk.aywalk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pk.aywalk.ui.theme.AYWALKTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AYWALKTheme {
                Navigation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}


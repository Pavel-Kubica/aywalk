package pk.aywalk.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.datetime.LocalDateTime
import pk.aywalk.R
import pk.aywalk.model.Connection
import pk.aywalk.model.ConnectionFinder
import pk.aywalk.model.Transfer
import java.time.Month
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResults (navController: NavController, from: String, to: String, time: java.time.LocalDateTime) {
    val searchResult = ConnectionFinder.findConnections(from, to, time)
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.results_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                           imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                           contentDescription = null
                        )
                    }
                })
        }
    ) {
        Row(modifier = Modifier.padding(it)) {
            ResultsColumn(modifier = Modifier.weight(0.3f), icon = Icons.AutoMirrored.Filled.DirectionsRun, cons = searchResult.comfort)
            ResultsColumn(modifier = Modifier.weight(0.3f), icon = Icons.Default.Bed, cons = searchResult.lazy)
            ResultsColumn(modifier = Modifier.weight(0.3f), icon = Icons.Default.Hiking , cons = searchResult.walky)
        }
    }
}

@Composable
fun ResultsColumn(modifier: Modifier = Modifier, icon: ImageVector, cons: Array<Connection>)
{
    Column (modifier = modifier.verticalScroll(rememberScrollState(0)),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(imageVector = icon, contentDescription = null)
        for (con in cons)
        {
            Connection(con = con)
        }
    }
}

@Composable
fun Connection(con: Connection)
{
    Column (modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Total walking time: ${con.totalWalkingTime}",
            modifier = Modifier.background(color = Color.White).fillMaxWidth().padding(4.dp),
            textAlign = TextAlign.Center)
        for (transfer in con.transfers) {
            Transfer(transfer = transfer)
        }
    }
}

@Composable
fun Transfer(modifier: Modifier = Modifier, transfer: Transfer)
{
    Column (
        modifier = modifier.background(color = Color.White),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row {
            Station(transfer.from)
            Spacer(modifier = Modifier.weight(0.5f))
            Text(transfer.departure.time.toString())
        }
        HorizontalDivider(modifier = Modifier.height(8.dp))
        Row (verticalAlignment = Alignment.Bottom) {
            Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = null)
            Text(text = transfer.line, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = " směr " + transfer.direction, fontStyle = FontStyle.Italic, fontSize = 12.sp)
        }
        HorizontalDivider()
        Row {
            Station(transfer.to)
            Spacer(modifier = Modifier.weight(0.5f))
            Text(transfer.arrival.time.toString())
        }
    }
}

@Composable
fun Station(station: String)
{
    Text(
        text = station,
        modifier = Modifier.background(color = Color.LightGray),
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold)
}

@Preview
@Composable
fun ConPreview()
{
    val exampleTransfer = Transfer(
        departure = LocalDateTime(year = 2024, month = Month.AUGUST, dayOfMonth = 19, hour = 12, minute = 49, second = 50),
        from = "Hlavní nádraží",
        to = "Petřiny",
        arrival = LocalDateTime(year = 2024, month = Month.AUGUST, dayOfMonth = 19, hour = 12, minute = 59, second = 50),
        line = "Tram 15",
        direction = "Lehovec")
    Connection(con = Connection(from = "Hlohová", totalWalkingTime = 20.minutes, transfers = arrayOf(exampleTransfer, exampleTransfer, exampleTransfer, exampleTransfer)))
}
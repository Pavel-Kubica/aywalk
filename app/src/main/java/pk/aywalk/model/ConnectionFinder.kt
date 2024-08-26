package pk.aywalk.model

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.util.SortedSet
import kotlin.time.Duration


object ConnectionFinder : ViewModel()
{

    fun findConnections(from: String, to: String, time: java.time.LocalDateTime): ConnectionFinderResult {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun buildStopData()
    {
        val stopsJson = getStops()
        val stopGroups = Json { ignoreUnknownKeys = true }.decodeFromString<GetStopsResponse>(string = stopsJson.string()).stopGroups
        stopsJson.close()
        stopData = stopGroups.toSortedSet { lhs, rhs -> lhs.name.compareTo(rhs.name) }
    }

    private const val MAX_WALKABLE_DIST_METERS = 500
    private const val STOPS_URL = "https://data.pid.cz/stops/json/stops.json"

    private lateinit var stopData: SortedSet<GetStopsResponse.Stop>

    private fun getStops(): ResponseBody {
        val client = OkHttpClient()
        val request = Request.Builder().url(STOPS_URL).build()
        return client.newCall(request).execute().body!!
    }

    private fun getNearestTo(latitude: Double, longitude: Double): List<String>
    {
        fun distanceFilter(stop: GetStopsResponse.Stop): Boolean
        {
            val resultArr = FloatArray(3)
            Location.distanceBetween(stop.latitude, stop.longitude, latitude, longitude, resultArr)
            return resultArr[0] < MAX_WALKABLE_DIST_METERS && resultArr[0] != 0f // Distance of 0 means we found ourselves
        }

        return stopData.filter(::distanceFilter).map { stop -> stop.name }
    }

    @Serializable
    data class GetStopsResponse(val stopGroups: List<Stop>) {

        @Serializable
        data class Stop(
            @SerialName("idosName") val name: String,
            @SerialName("avgLat") val latitude: Double,
            @SerialName("avgLon") val longitude: Double) : Comparable<Stop> {
            override fun compareTo(other: Stop): Int {
                return name.compareTo(other.name)
            }

        }
    }
}



data class ConnectionFinderResult(val comfort: Array<Connection>,
                                  val lazy: Array<Connection>,
                                  val walky: Array<Connection>
)

data class Connection(
    val from: String,
    val transfers: Array<Transfer>,
    val totalWalkingTime: Duration
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Connection

        return transfers.contentEquals(other.transfers)
    }

    override fun hashCode(): Int {
        return transfers.contentHashCode()
    }
}

data class Transfer(
    val departure: LocalDateTime,
    val from: String,
    val line: String,
    val arrival: LocalDateTime,
    val to: String,
    val direction: String
)
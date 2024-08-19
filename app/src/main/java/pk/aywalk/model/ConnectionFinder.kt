package pk.aywalk.model

import kotlinx.datetime.LocalDateTime
import kotlin.time.Duration


class ConnectionFinder 
{
    companion object {
        fun findConnections(from: String, to: String): ConnectionFinderResult {
            TODO("Not yet implemented")
        }
    }
}

data class ConnectionFinderResult(val comfort: Array<Connection>,
                                  val lazy: Array<Connection>,
                                  val walky: Array<Connection>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConnectionFinderResult

        if (!comfort.contentEquals(other.comfort)) return false
        if (!lazy.contentEquals(other.lazy)) return false
        if (!walky.contentEquals(other.walky)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = comfort.contentHashCode()
        result = 31 * result + lazy.contentHashCode()
        result = 31 * result + walky.contentHashCode()
        return result
    }
}

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
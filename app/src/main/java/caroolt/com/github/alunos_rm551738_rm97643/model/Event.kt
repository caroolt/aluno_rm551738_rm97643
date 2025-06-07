package caroolt.com.github.alunos_rm551738_rm97643.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val localName: String,
    val eventType: String,
    val impactLevel: String,
    val eventDate: String,
    val affectedPeople: Int
) 
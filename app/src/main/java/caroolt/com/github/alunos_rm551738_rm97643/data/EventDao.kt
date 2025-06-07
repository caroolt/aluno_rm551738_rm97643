package caroolt.com.github.alunos_rm551738_rm97643.data

import androidx.lifecycle.LiveData
import androidx.room.*
import caroolt.com.github.alunos_rm551738_rm97643.model.Event

@Dao
interface EventDao {
    
    @Query("SELECT * FROM events ORDER BY id DESC")
    fun getAllEvents(): LiveData<List<Event>>
    
    @Insert
    suspend fun insertEvent(event: Event)
    
    @Delete
    suspend fun deleteEvent(event: Event)
    
    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun deleteEventById(eventId: Long)
} 
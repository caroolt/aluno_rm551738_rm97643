package caroolt.com.github.alunos_rm551738_rm97643.repository

import androidx.lifecycle.LiveData
import caroolt.com.github.alunos_rm551738_rm97643.data.EventDao
import caroolt.com.github.alunos_rm551738_rm97643.model.Event

class EventRepository(private val eventDao: EventDao) {
    
    val allEvents: LiveData<List<Event>> = eventDao.getAllEvents()
    
    suspend fun insert(event: Event) {
        eventDao.insertEvent(event)
    }
    
    suspend fun delete(event: Event) {
        eventDao.deleteEvent(event)
    }
    
    suspend fun deleteById(eventId: Long) {
        eventDao.deleteEventById(eventId)
    }
} 
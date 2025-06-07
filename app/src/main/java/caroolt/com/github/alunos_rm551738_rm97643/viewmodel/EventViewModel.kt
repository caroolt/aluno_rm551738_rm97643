package caroolt.com.github.alunos_rm551738_rm97643.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import caroolt.com.github.alunos_rm551738_rm97643.data.EventDatabase
import caroolt.com.github.alunos_rm551738_rm97643.model.Event
import caroolt.com.github.alunos_rm551738_rm97643.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: EventRepository
    val allEvents: LiveData<List<Event>>
    
    init {
        val eventDao = EventDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDao)
        allEvents = repository.allEvents
    }
    
    fun insert(event: Event) = viewModelScope.launch {
        repository.insert(event)
    }
    
    fun delete(event: Event) = viewModelScope.launch {
        repository.delete(event)
    }
    
    fun deleteById(eventId: Long) = viewModelScope.launch {
        repository.deleteById(eventId)
    }
} 
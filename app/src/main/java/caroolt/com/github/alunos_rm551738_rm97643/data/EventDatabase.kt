package caroolt.com.github.alunos_rm551738_rm97643.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import caroolt.com.github.alunos_rm551738_rm97643.model.Event

@Database(
    entities = [Event::class],
    version = 1,
    exportSchema = false
)
abstract class EventDatabase : RoomDatabase() {
    
    abstract fun eventDao(): EventDao
    
    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null
        
        fun getDatabase(context: Context): EventDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "event_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 
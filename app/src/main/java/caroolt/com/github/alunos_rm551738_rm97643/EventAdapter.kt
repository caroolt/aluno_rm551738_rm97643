package caroolt.com.github.alunos_rm551738_rm97643

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import caroolt.com.github.alunos_rm551738_rm97643.model.Event

class EventAdapter(private val onDeleteClick: (Event) -> Unit) : 
    ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onDeleteClick)
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val localNameText: TextView = itemView.findViewById(R.id.tv_local_name)
        private val eventTypeText: TextView = itemView.findViewById(R.id.tv_event_type)
        private val impactLevelText: TextView = itemView.findViewById(R.id.tv_impact_level)
        private val eventDateText: TextView = itemView.findViewById(R.id.tv_event_date)
        private val affectedPeopleText: TextView = itemView.findViewById(R.id.tv_affected_people)
        private val deleteButton: Button = itemView.findViewById(R.id.btn_delete)

        fun bind(event: Event, onDeleteClick: (Event) -> Unit) {
            localNameText.text = "Local: ${event.localName}"
            eventTypeText.text = "Evento: ${event.eventType}"
            impactLevelText.text = "Impacto: ${event.impactLevel}"
            eventDateText.text = "Data: ${event.eventDate}"
            affectedPeopleText.text = "Pessoas afetadas: ${event.affectedPeople}"
            
            deleteButton.setOnClickListener {
                onDeleteClick(event)
            }
        }
    }

    class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }
} 
package caroolt.com.github.alunos_rm551738_rm97643

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import caroolt.com.github.alunos_rm551738_rm97643.model.Event
import caroolt.com.github.alunos_rm551738_rm97643.viewmodel.EventViewModel

class MainActivity : AppCompatActivity() {
    
    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var eventAdapter: SimpleEventAdapter
    
    // Views
    private lateinit var etLocalName: EditText
    private lateinit var spinnerEventType: Spinner
    private lateinit var spinnerImpactLevel: Spinner
    private lateinit var etEventDate: EditText
    private lateinit var etAffectedPeople: EditText
    private lateinit var btnInclude: Button
    private lateinit var btnIdentification: Button
    private lateinit var rvEvents: RecyclerView
    private lateinit var tvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Criar layout programaticamente
        createLayout()
        setupRecyclerView()
        setupSpinners()
        setupObservers()
        setupClickListeners()
    }

    private fun createLayout() {
        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }
        
        // Título
        tvTitle = TextView(this).apply {
            text = "🌪️ Registro de Eventos Extremos 🌍"
            textSize = 20f
            setPadding(16, 16, 16, 32)
            setBackgroundColor(0xFFE8F5E8.toInt())
            setTextColor(0xFF2E7D32.toInt())
            gravity = android.view.Gravity.CENTER
        }
        mainLayout.addView(tvTitle)
        
        // ScrollView para os campos
        val scrollView = ScrollView(this)
        val formLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 16, 0, 16)
        }
        
        // Campo Nome do Local
        val tvLocalLabel = TextView(this).apply {
            text = "Nome do local (cidade/bairro):"
            textSize = 16f
            setPadding(0, 16, 0, 8)
        }
        etLocalName = EditText(this).apply {
            hint = "Ex: São Paulo - Vila Madalena"
            setPadding(16, 16, 16, 16)
            setBackgroundResource(android.R.drawable.edit_text)
        }
        formLayout.addView(tvLocalLabel)
        formLayout.addView(etLocalName)
        
        // Campo Tipo do Evento
        val tvEventTypeLabel = TextView(this).apply {
            text = "Tipo do evento extremo:"
            textSize = 16f
            setPadding(0, 16, 0, 8)
        }
        spinnerEventType = Spinner(this)
        formLayout.addView(tvEventTypeLabel)
        formLayout.addView(spinnerEventType)
        
        // Campo Grau de Impacto
        val tvImpactLabel = TextView(this).apply {
            text = "Grau de impacto:"
            textSize = 16f
            setPadding(0, 16, 0, 8)
        }
        spinnerImpactLevel = Spinner(this)
        formLayout.addView(tvImpactLabel)
        formLayout.addView(spinnerImpactLevel)
        
        // Campo Data do Evento
        val tvDateLabel = TextView(this).apply {
            text = "Data do evento (DD/MM/AAAA):"
            textSize = 16f
            setPadding(0, 16, 0, 8)
        }
        etEventDate = EditText(this).apply {
            hint = "Ex: 15/12/2024"
            inputType = android.text.InputType.TYPE_CLASS_DATETIME
            setPadding(16, 16, 16, 16)
            setBackgroundResource(android.R.drawable.edit_text)
        }
        formLayout.addView(tvDateLabel)
        formLayout.addView(etEventDate)
        
        // Campo Pessoas Afetadas
        val tvPeopleLabel = TextView(this).apply {
            text = "Número estimado de pessoas afetadas:"
            textSize = 16f
            setPadding(0, 16, 0, 8)
        }
        etAffectedPeople = EditText(this).apply {
            hint = "Ex: 1500"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            setPadding(16, 16, 16, 16)
            setBackgroundResource(android.R.drawable.edit_text)
        }
        formLayout.addView(tvPeopleLabel)
        formLayout.addView(etAffectedPeople)
        
        // Botão Incluir
        btnInclude = Button(this).apply {
            text = "INCLUIR"
            textSize = 18f
            setPadding(16, 20, 16, 20)
            setBackgroundColor(0xFF4CAF50.toInt())
            setTextColor(0xFFFFFFFF.toInt())
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 32, 0, 16)
            layoutParams = params
        }
        formLayout.addView(btnInclude)
        
        // Botão Identificação
        btnIdentification = Button(this).apply {
            text = "Ver Identificação"
            textSize = 16f
            setPadding(16, 16, 16, 16)
            setBackgroundColor(0xFF2196F3.toInt())
            setTextColor(0xFFFFFFFF.toInt())
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, 24)
            layoutParams = params
        }
        formLayout.addView(btnIdentification)
        
        scrollView.addView(formLayout)
        mainLayout.addView(scrollView)
        
        // RecyclerView
        rvEvents = RecyclerView(this).apply {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            layoutParams = params
        }
        mainLayout.addView(rvEvents)
        
        setContentView(mainLayout)
    }

    private fun setupRecyclerView() {
        eventAdapter = SimpleEventAdapter { event ->
            eventViewModel.delete(event)
            Toast.makeText(this, "Evento excluído com sucesso!", Toast.LENGTH_SHORT).show()
        }
        
        rvEvents.adapter = eventAdapter
        rvEvents.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSpinners() {
        // Tipos de eventos extremos
        val eventTypes = arrayOf(
            "Selecione o tipo...",
            "Chuva intensa", "Seca", "Onda de calor", "Onda de frio",
            "Vendaval", "Granizo", "Enchente", "Deslizamento",
            "Tornado", "Furacão", "Incêndio florestal", "Tempestade"
        )
        
        val eventTypeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, eventTypes)
        eventTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEventType.adapter = eventTypeAdapter

        // Graus de impacto
        val impactLevels = arrayOf("Selecione o impacto...", "Leve", "Moderado", "Severo")
        val impactAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, impactLevels)
        impactAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerImpactLevel.adapter = impactAdapter
    }

    private fun setupObservers() {
        eventViewModel.allEvents.observe(this) { events ->
            eventAdapter.submitList(events)
        }
    }

    private fun setupClickListeners() {
        btnInclude.setOnClickListener {
            addEvent()
        }

        btnIdentification.setOnClickListener {
            showIdentification()
        }
        
        // Funcionalidade bônus: botão para filtrar eventos por impacto severo
        val btnFilterSevere = Button(this).apply {
            text = "Filtrar Severos"
            textSize = 14f
            setBackgroundColor(0xFFFF9800.toInt())
            setTextColor(0xFFFFFFFF.toInt())
            setPadding(16, 12, 16, 12)
            setOnClickListener { 
                filterSevereEvents() 
            }
        }
        
        val btnShowAll = Button(this).apply {
            text = "Mostrar Todos"
            textSize = 14f
            setBackgroundColor(0xFF607D8B.toInt())
            setTextColor(0xFFFFFFFF.toInt())
            setPadding(16, 12, 16, 12)
            setOnClickListener { 
                showAllEvents() 
            }
        }
        
        // Adicionar botões de filtro ao layout
        val filterLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 16, 0, 16)
            layoutParams = params
        }
        
        val btnFilterParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        btnFilterParams.setMargins(0, 0, 8, 0)
        btnFilterSevere.layoutParams = btnFilterParams
        
        val btnShowParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        btnShowParams.setMargins(8, 0, 0, 0)
        btnShowAll.layoutParams = btnShowParams
        
        filterLayout.addView(btnFilterSevere)
        filterLayout.addView(btnShowAll)
        
        // Inserir filtros antes do RecyclerView
        val parent = rvEvents.parent as LinearLayout
        val index = parent.indexOfChild(rvEvents)
        parent.addView(filterLayout, index)
    }

    private fun addEvent() {
        val localName = etLocalName.text.toString().trim()
        val eventType = if (spinnerEventType.selectedItemPosition > 0) 
            spinnerEventType.selectedItem.toString() else ""
        val impactLevel = if (spinnerImpactLevel.selectedItemPosition > 0) 
            spinnerImpactLevel.selectedItem.toString() else ""
        val eventDate = etEventDate.text.toString().trim()
        val affectedPeopleStr = etAffectedPeople.text.toString().trim()

        // Validações
        if (localName.isEmpty()) {
            etLocalName.error = "Nome do local é obrigatório"
            etLocalName.requestFocus()
            return
        }

        if (eventType.isEmpty()) {
            Toast.makeText(this, "Selecione o tipo do evento", Toast.LENGTH_SHORT).show()
            return
        }

        if (impactLevel.isEmpty()) {
            Toast.makeText(this, "Selecione o grau de impacto", Toast.LENGTH_SHORT).show()
            return
        }

        if (eventDate.isEmpty()) {
            etEventDate.error = "Data do evento é obrigatória"
            etEventDate.requestFocus()
            return
        }

        if (affectedPeopleStr.isEmpty()) {
            etAffectedPeople.error = "Número de pessoas afetadas é obrigatório"
            etAffectedPeople.requestFocus()
            return
        }

        val affectedPeople = try {
            affectedPeopleStr.toInt()
        } catch (e: NumberFormatException) {
            etAffectedPeople.error = "Número inválido"
            etAffectedPeople.requestFocus()
            return
        }

        if (affectedPeople <= 0) {
            etAffectedPeople.error = "Número deve ser maior que zero"
            etAffectedPeople.requestFocus()
            return
        }

        // Criar evento
        val event = Event(
            localName = localName,
            eventType = eventType,
            impactLevel = impactLevel,
            eventDate = eventDate,
            affectedPeople = affectedPeople
        )

        eventViewModel.insert(event)
        clearFields()
        Toast.makeText(this, "Evento adicionado com sucesso!", Toast.LENGTH_SHORT).show()
    }

    private fun clearFields() {
        etLocalName.text.clear()
        spinnerEventType.setSelection(0)
        spinnerImpactLevel.setSelection(0)
        etEventDate.text.clear()
        etAffectedPeople.text.clear()
    }
    
    private fun showIdentification() {
        AlertDialog.Builder(this)
            .setTitle("👥 Identificação da Equipe")
            .setMessage("""
                🎓 Turma: 3SIR - GS1 2025
                👨‍🏫 Professor: Ewerton Carreira
                🌪️ Tema: Eventos Extremos
                
                👤 Aluno 1: Daniel Marin Palma - RM551738
                👤 Aluno 2: Carolina Teixeira Coelho - RM97643
                
                📱 Aplicativo desenvolvido em Android + Kotlin
                🏗️ Arquitetura: MVVM com Room Database
                
                🎯 Funcionalidades Implementadas:
                ✅ Cadastro de eventos extremos
                ✅ Validação completa de campos
                ✅ Persistência com SQLite/Room
                ✅ RecyclerView customizado
                ✅ Exclusão individual de itens
                ✅ Filtros por tipo de impacto (BÔNUS)
            """.trimIndent())
            .setPositiveButton("OK", null)
            .show()
    }
    
    // Funcionalidades bônus: filtros de eventos
    private fun filterSevereEvents() {
        eventViewModel.allEvents.value?.let { allEvents ->
            val severeEvents = allEvents.filter { it.impactLevel == "Severo" }
            eventAdapter.submitList(severeEvents)
            Toast.makeText(this, "Mostrando apenas eventos severos (${severeEvents.size})", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun showAllEvents() {
        eventViewModel.allEvents.value?.let { allEvents ->
            eventAdapter.submitList(allEvents)
            Toast.makeText(this, "Mostrando todos os eventos (${allEvents.size})", Toast.LENGTH_SHORT).show()
        }
    }
}

// Adapter simples para o RecyclerView
class SimpleEventAdapter(private val onDeleteClick: (Event) -> Unit) : 
    RecyclerView.Adapter<SimpleEventAdapter.ViewHolder>() {
    
    private var events = listOf<Event>()
    
    fun submitList(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 24, 32, 24)
            setBackgroundColor(0xFFFFFFFF.toInt())
            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(16, 8, 16, 8)
            layoutParams = params
            elevation = 8f
        }
        
        return ViewHolder(layout)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position], onDeleteClick)
    }
    
    override fun getItemCount() = events.size
    
    class ViewHolder(private val layout: LinearLayout) : RecyclerView.ViewHolder(layout) {
        fun bind(event: Event, onDeleteClick: (Event) -> Unit) {
            layout.removeAllViews()
            
            // Header com nome do local e botão excluir
            val headerLayout = LinearLayout(layout.context).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            
            val localText = TextView(layout.context).apply {
                text = "📍 ${event.localName}"
                textSize = 18f
                setTextColor(0xFF1976D2.toInt())
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                setPadding(0, 0, 16, 0)
            }
            
            val deleteButton = Button(layout.context).apply {
                text = "Excluir"
                textSize = 12f
                setBackgroundColor(0xFFF44336.toInt())
                setTextColor(0xFFFFFFFF.toInt())
                setPadding(16, 8, 16, 8)
                setOnClickListener { onDeleteClick(event) }
            }
            
            headerLayout.addView(localText)
            headerLayout.addView(deleteButton)
            layout.addView(headerLayout)
            
            // Informações do evento
            val infoLayout = LinearLayout(layout.context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(0, 16, 0, 0)
                setBackgroundColor(0xFFF8F9FA.toInt())
                setPadding(16, 16, 16, 16)
            }
            
            val eventTypeText = TextView(layout.context).apply {
                text = "🌪️ Evento: ${event.eventType}"
                textSize = 16f
                setPadding(0, 4, 0, 4)
            }
            
            val impactText = TextView(layout.context).apply {
                text = "⚠️ Impacto: ${event.impactLevel}"
                textSize = 16f
                setPadding(0, 4, 0, 4)
            }
            
            val dateText = TextView(layout.context).apply {
                text = "📅 Data: ${event.eventDate}"
                textSize = 16f
                setPadding(0, 4, 0, 4)
            }
            
            val peopleText = TextView(layout.context).apply {
                text = "👥 Pessoas afetadas: ${event.affectedPeople}"
                textSize = 16f
                setPadding(0, 4, 0, 0)
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            
            infoLayout.addView(eventTypeText)
            infoLayout.addView(impactText)
            infoLayout.addView(dateText)
            infoLayout.addView(peopleText)
            
            layout.addView(infoLayout)
        }
    }
} 
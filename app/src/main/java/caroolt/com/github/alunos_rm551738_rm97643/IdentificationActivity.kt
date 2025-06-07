package caroolt.com.github.alunos_rm551738_rm97643

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class IdentificationActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identification)
        
        val btnBack: Button = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }
    }
} 
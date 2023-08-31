package co.heversonasc.haffit

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import java.util.*

class WaterActivity : AppCompatActivity() {

    private lateinit var edit_peso: EditText
    private lateinit var edit_idade: EditText
    private lateinit var bt_calculate: Button
    private lateinit var txt_result_water : TextView
    private lateinit var bt_lembrete: Button
    private lateinit var bt_alarme: Button
    private lateinit var txt_hora: TextView
    private lateinit var txt_minutos: TextView


    private lateinit var calcularIngestaoDeDiaria: CalcularIngestaoDeDiaria
    private  var resultadoML = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendario: Calendar
    var horaAtual = 0
    var minutosAtuais = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water)

        supportActionBar?.hide()

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = true
        }

        calcularIngestaoDeDiaria = CalcularIngestaoDeDiaria()

        edit_peso = findViewById (R.id.edit_peso)
        edit_idade = findViewById (R.id.edit_idade)
        bt_calculate = findViewById(R.id.bt_calcular)
        txt_result_water = findViewById(R.id.txt_result_water)
        bt_lembrete = findViewById(R.id.bt_lembrete)
        bt_alarme = findViewById(R.id.bt_alarme)
        txt_hora = findViewById(R.id.txt_hora)
        txt_minutos= findViewById(R.id.txt_minutos)



        bt_calculate.setOnClickListener {
            if (edit_peso.text.toString().isEmpty()){
                Toast.makeText(this,R.string.toast_informe_peso, Toast.LENGTH_SHORT).show()
            } else if(edit_idade.text.toString().isEmpty()) {
                Toast.makeText(this,R.string.toast_informe_idade, Toast.LENGTH_SHORT).show()
            }else {
                val peso = edit_peso.text.toString().toDouble()
                val idade = edit_idade.text.toString().toInt()
                calcularIngestaoDeDiaria.CalcularTotalML(peso, idade)
                resultadoML= calcularIngestaoDeDiaria.ResultadoML()
                txt_result_water.text = resultadoML.toString() + " " + "Litros"


            }
        }
        bt_lembrete.setOnClickListener {
            calendario = Calendar.getInstance()
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
            minutosAtuais = calendario.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this, {timePicker: TimePicker, hourOfDay: Int, minutes:Int ->
                txt_hora.text = String.format("%02d", hourOfDay)
                txt_minutos.text = String.format("%02d", minutes)
            }, horaAtual, minutosAtuais,true)
            timePickerDialog.show()
        }

        bt_alarme.setOnClickListener {
            if (!txt_hora.text.toString().isEmpty() && !txt_minutos.text.toString().isEmpty()) {
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, txt_hora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, txt_minutos.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarme_messagem))
                startActivity(intent)

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }
        }
    }


}





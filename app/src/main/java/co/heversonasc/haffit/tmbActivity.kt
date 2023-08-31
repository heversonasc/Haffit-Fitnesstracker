package co.heversonasc.haffit

import android.R.layout.simple_list_item_1
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import co.heversonasc.haffit.model.Calc

class tmbActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var editAge: EditText

    private lateinit var lifestyle: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = true
        }

        setSupportActionBar(findViewById(R.id.tmb_toolbar))

        editWeight = findViewById(R.id.edit_tmb_weight)
        editHeight = findViewById(R.id.edit_tmb_height)
        editAge = findViewById(R.id.edit_tmb_age)

        lifestyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        lifestyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lifestyle.setAdapter(adapter)

        val btnSend: Button = findViewById(R.id.btn_tmb_send)
        btnSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_messages, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()
            val age = editAge.text.toString().toInt()

            val result = calculateTmb(weight, height, age)
            val response = tmbRequest(result)
            if(response == 0.0) {
                Toast.makeText(this, R.string.fields_messages, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage(getString(R.string.tmb_response, response))
                .setPositiveButton(android.R.string.ok) {p0, which -> }
                .setNegativeButton(R.string.save) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.CalcDao()
                        dao.insert(Calc(type = "tmb", res = response))

                        runOnUiThread {
                            openListActivity()
                        }
                    }.start()
                }
                .create()
                .show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search){
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListActivity() {
        val intent = Intent(this, ListCalcActivity::class.java)
        intent.putExtra("type", "tmb")
        startActivity(intent)
    }

    private fun tmbRequest(tmb: Double) : Double {
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        return when {
            lifestyle.text.toString() == items [0] -> tmb * 1.2
            lifestyle.text.toString() == items [1] -> tmb * 1.375
            lifestyle.text.toString() == items [2] -> tmb * 1.55
            lifestyle.text.toString() == items [3] -> tmb * 1.725
            lifestyle.text.toString() == items [4] -> tmb * 1.9
            else -> 0.0
        }
    }

    private fun calculateTmb(weight: Int, height: Int, age: Int): Double {
        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
    }

    private fun validate(): Boolean {
        return (editWeight.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0")
                && editHeight.text.toString().isNotEmpty()
                && !editHeight.text.toString().startsWith("0")
                && editAge.text.toString().isNotEmpty()
                && !editAge.text.toString().startsWith("0")
                )
    }
}
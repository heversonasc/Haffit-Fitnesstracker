package co.heversonasc.haffit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.heversonasc.haffit.model.Calc
import java.text.SimpleDateFormat
import java.util.*

class ListCalcActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = true
        }

        val result = mutableListOf<Calc>()
        val adapter = ListCalcAdapter(result)
        rv = findViewById(R.id.rv_list)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val type = intent?.extras?.getString("type")?: throw IllegalStateException("type not found")
        Thread {
            val app = application as App
            val dao = app.db.CalcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                result.addAll(response)
                adapter.notifyDataSetChanged()

            }

        }.start()
    }
        private inner class ListCalcAdapter(private val listCalc: List<Calc>
        ): RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
                val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
                return ListCalcViewHolder (view)

            }

            override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
                val itemCurrent = listCalc[position]
                holder.bind(itemCurrent)


            }

            override fun getItemCount(): Int {
                return listCalc.size
            }

            private inner class ListCalcViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
                fun bind(item: Calc) {


                    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt","BR") )
                    val data = sdf.format(item.createdDate)
                    val res = item.res

                    val tv = itemView as TextView
                    tv.text = getString(R.string.list_response,res,data)




                    }

                }
            }

        }

package co.heversonasc.haffit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.green
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.heversonasc.haffit.*

class MainActivity : AppCompatActivity() {

//    private lateinit var btnimc: LinearLayout
    private lateinit var rvMain: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = true
        }


        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.imccolor,
                textStringId = R.string.label_imc
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.tmbcolor,
                textStringId = R.string.label_tmb
            )
        )
        mainItems.add(
            MainItem(
                id = 3,
                drawableId = R.drawable.watercolor,
                textStringId = R.string.label_water
            )
        )
        mainItems.add(
            MainItem(
                id = 4,
                drawableId = R.drawable.ideascolor,
                textStringId = R.string.label_dicas
            )
        )



        val adapter = MainAdapter (mainItems) { id ->
                when(id){
                    1-> {
                        val intent = Intent(this, ImcActivity::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this, tmbActivity::class.java)
                        startActivity(intent)

                    }
                    3 -> {
                        val intent = Intent(this, WaterActivity::class.java)
                        startActivity(intent)
                    }
                    4 -> {
                        val intent = Intent(this, tipsHealth::class.java)
                        startActivity(intent)
                    }

                }
                Log.i("teste", "clicou $id")
            }

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this,2)

    }


    private inner class MainAdapter(private val mainItems: List<MainItem>,
                                    private val OnItemClickListener: (Int) -> Unit,
    ): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder (view)

        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)




        }

        override fun getItemCount(): Int {
            return mainItems.size
        }


        private inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)




                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)


                container.setOnClickListener {
                    OnItemClickListener.invoke(item.id)

                }

                    }
                }


            }
        }











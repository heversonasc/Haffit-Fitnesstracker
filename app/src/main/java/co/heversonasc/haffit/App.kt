package co.heversonasc.haffit

import android.app.Application
import co.heversonasc.haffit.model.AppDatabase

class App: Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }

}
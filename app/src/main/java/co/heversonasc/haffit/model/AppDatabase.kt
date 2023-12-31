package co.heversonasc.haffit.model

import android.content.Context
import androidx.room.*

@Database(entities = [Calc::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CalcDao(): CalcDao

    companion object {

        private var INSTANCE: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase {
            return if (INSTANCE == null) {

                synchronized(this) {

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "haffit"
                    ).build()
                }
                INSTANCE as AppDatabase

            } else {
                INSTANCE as AppDatabase
            }


        }

    }
}
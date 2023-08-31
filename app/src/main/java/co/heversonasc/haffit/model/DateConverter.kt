package co.heversonasc.haffit.model

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

   @TypeConverter
   fun toDate (dateLong: Long?): Date? {
       return if (dateLong != null) Date(dateLong) else null

   }

    @TypeConverter
    fun fromdate(date:Date?): Long? {
        return date?.time
    }
}
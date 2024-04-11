package kg.geekspro.android_lotos.presentation.ui.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kg.geekspro.android_lotos.presentation.ui.model.PersonalData

@Database(entities = [PersonalData::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun appDao():AppDao
}
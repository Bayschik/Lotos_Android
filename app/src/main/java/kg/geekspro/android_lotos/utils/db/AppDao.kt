package kg.geekspro.android_lotos.utils.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kg.geekspro.android_lotos.models.orderHistoryModel.PersonalData

@Dao
interface AppDao {

    @Query("SELECT * FROM personaldata")
    fun getAll(): PersonalData

    @Insert
    fun insert(data: PersonalData)

    @Update
    fun update(data: PersonalData)
}
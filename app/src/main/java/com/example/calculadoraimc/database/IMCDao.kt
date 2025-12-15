package com.example.calculadoraimc.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IMCDao {
    @Insert
    suspend fun insert(imcResult: IMCResultEntity)

    @Query("SELECT * FROM imc_history ORDER BY date DESC")
    fun getAllHistory(): Flow<List<IMCResultEntity>>

    @Delete
    suspend fun delete(imcResult: IMCResultEntity)
}
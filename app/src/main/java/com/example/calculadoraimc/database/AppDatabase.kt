package com.example.calculadoraimc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. MUDAMOS A VERSÃO DE 1 PARA 2
@Database(entities = [IMCResultEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imcDao(): IMCDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "imc_database"
                )
                    // 2. ADICIONAMOS ESTA LINHA MÁGICA
                    // Ela permite que o banco seja recriado se a estrutura mudar
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
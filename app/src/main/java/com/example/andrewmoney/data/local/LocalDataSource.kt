package com.example.andrewmoney.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.andrewmoney.data.local.dao.HistoryDAO
import com.example.andrewmoney.data.local.dao.VaultDAO
import com.example.andrewmoney.data.local.model.LocalHistoryModel
import com.example.andrewmoney.data.local.model.LocalVaultModel

@Database(entities = [LocalVaultModel::class, LocalHistoryModel::class], version = 1)
abstract class LocalDataSource : RoomDatabase() {

    abstract fun vaultDAO() : VaultDAO
    abstract fun historyDAO() : HistoryDAO

    companion object{
        @Volatile
        private var INSTANCE: LocalDataSource? = null

        fun getDatabase(context: Context): LocalDataSource {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        LocalDataSource::class.java,
                        "DB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}
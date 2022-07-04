package com.example.andrewmoney.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andrewmoney.data.local.model.LocalVaultModel

@Dao
interface VaultDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushAllVaults(vaults: List<LocalVaultModel>)

    @Query("SELECT * FROM vault ORDER BY isLiked DESC")
    suspend fun getVaults(): List<LocalVaultModel>

    @Query("UPDATE vault SET rate = :rate WHERE name = :name")
    suspend fun updateVault(name: String, rate: Double);

    @Query("UPDATE vault SET isLiked = :isLiked WHERE name = :name")
    suspend fun updateIsLiked(name: String, isLiked: Boolean)

    @Query("DELETE FROM vault")
    suspend fun nukeTable()
}
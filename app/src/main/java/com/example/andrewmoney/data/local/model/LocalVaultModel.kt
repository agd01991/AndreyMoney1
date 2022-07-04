package com.example.andrewmoney.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vault")
data class LocalVaultModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val rate: Double,
    val isLiked: Boolean
)

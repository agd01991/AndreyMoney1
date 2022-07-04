package com.example.andrewmoney.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class LocalHistoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val firstAmount: String,
    val secondAmount: String
)

package com.thoni.mvpprojetoismael.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "epi_types")
data class EpiTypeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val monthsValidity: Int,
    val synced: Boolean,
    val updatedAt: Long
)

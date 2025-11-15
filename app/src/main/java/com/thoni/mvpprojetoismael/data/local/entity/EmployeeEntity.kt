package com.thoni.mvpprojetoismael.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val sector: String?,
    val role: String?,
    val shoeSize: String?,
    val uniformSize: String?,
    val active: Boolean,
    val synced: Boolean,
    val updatedAt: Long
)

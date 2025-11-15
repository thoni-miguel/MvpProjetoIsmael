package com.thoni.mvpprojetoismael.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deliveries")
data class DeliveryEntity(
    @PrimaryKey val id: String,
    val employeeId: String,
    val epiTypeId: String,
    val deliveredAt: Long,
    val dueAt: Long?,
    val confirmMethod: String?,
    val photoPath: String?,
    val synced: Boolean,
    val updatedAt: Long
)

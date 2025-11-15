package com.thoni.mvpprojetoismael.domain.model

data class Delivery(
    val id: String,
    val employeeId: String,
    val epiTypeId: String,
    val deliveredAt: Long,
    val dueAt: Long?,
    val confirmMethod: String?,
    val photoPath: String?,
    val synced: Boolean,
    val updatedAt: Long
)

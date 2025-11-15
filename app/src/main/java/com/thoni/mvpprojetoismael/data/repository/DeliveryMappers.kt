package com.thoni.mvpprojetoismael.data.repository

import com.thoni.mvpprojetoismael.data.local.entity.DeliveryEntity
import com.thoni.mvpprojetoismael.domain.model.Delivery

fun DeliveryEntity.toDomain(): Delivery = Delivery(
    id = id,
    employeeId = employeeId,
    epiTypeId = epiTypeId,
    deliveredAt = deliveredAt,
    dueAt = dueAt,
    confirmMethod = confirmMethod,
    photoPath = photoPath,
    synced = synced,
    updatedAt = updatedAt
)

fun Delivery.toEntity(): DeliveryEntity = DeliveryEntity(
    id = id,
    employeeId = employeeId,
    epiTypeId = epiTypeId,
    deliveredAt = deliveredAt,
    dueAt = dueAt,
    confirmMethod = confirmMethod,
    photoPath = photoPath,
    synced = synced,
    updatedAt = updatedAt
)

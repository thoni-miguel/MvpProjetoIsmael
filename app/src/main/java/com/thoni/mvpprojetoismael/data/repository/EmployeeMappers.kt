package com.thoni.mvpprojetoismael.data.repository

import com.thoni.mvpprojetoismael.data.local.entity.EmployeeEntity
import com.thoni.mvpprojetoismael.domain.model.Employee

fun EmployeeEntity.toDomain(): Employee = Employee(
    id = id,
    name = name,
    sector = sector,
    role = role,
    shoeSize = shoeSize,
    uniformSize = uniformSize,
    active = active,
    synced = synced,
    updatedAt = updatedAt
)

fun Employee.toEntity(): EmployeeEntity = EmployeeEntity(
    id = id,
    name = name,
    sector = sector,
    role = role,
    shoeSize = shoeSize,
    uniformSize = uniformSize,
    active = active,
    synced = synced,
    updatedAt = updatedAt
)

package com.thoni.mvpprojetoismael.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thoni.mvpprojetoismael.data.local.dao.AuditLogDao
import com.thoni.mvpprojetoismael.data.local.dao.DeliveriesDao
import com.thoni.mvpprojetoismael.data.local.dao.EmployeesDao
import com.thoni.mvpprojetoismael.data.local.dao.EpiTypesDao
import com.thoni.mvpprojetoismael.data.local.entity.AuditLogEntity
import com.thoni.mvpprojetoismael.data.local.entity.DeliveryEntity
import com.thoni.mvpprojetoismael.data.local.entity.EmployeeEntity
import com.thoni.mvpprojetoismael.data.local.entity.EpiTypeEntity

@Database(
    entities = [
        EmployeeEntity::class,
        EpiTypeEntity::class,
        DeliveryEntity::class,
        AuditLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeesDao(): EmployeesDao
    abstract fun epiTypesDao(): EpiTypesDao
    abstract fun deliveriesDao(): DeliveriesDao
    abstract fun auditLogDao(): AuditLogDao
}

package com.thoni.mvpprojetoismael.core.di

import android.content.Context
import androidx.room.Room
import com.thoni.mvpprojetoismael.core.database.AppDatabase
import com.thoni.mvpprojetoismael.data.repository.DeliveryRepositoryImpl
import com.thoni.mvpprojetoismael.data.repository.EmployeeRepositoryImpl
import com.thoni.mvpprojetoismael.data.repository.EpiTypeRepositoryImpl
import com.thoni.mvpprojetoismael.domain.repository.EmployeeRepository
import com.thoni.mvpprojetoismael.domain.repository.EpiTypeRepository
import com.thoni.mvpprojetoismael.domain.repository.DeliveryRepository
import com.thoni.mvpprojetoismael.domain.usecase.AddEmployeeUseCase
import com.thoni.mvpprojetoismael.domain.usecase.AddEpiTypeUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveDeliveriesUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEmployeesUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEpiTypesUseCase
import com.thoni.mvpprojetoismael.domain.usecase.RegisterDeliveryUseCase

class AppContainer(context: Context) {
    private val database: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val employeeRepository: EmployeeRepository by lazy {
        EmployeeRepositoryImpl(database.employeesDao())
    }

    private val epiTypeRepository: EpiTypeRepository by lazy {
        EpiTypeRepositoryImpl(database.epiTypesDao())
    }

    private val deliveryRepository: DeliveryRepository by lazy {
        DeliveryRepositoryImpl(database.deliveriesDao())
    }

    val observeEmployeesUseCase: ObserveEmployeesUseCase by lazy {
        ObserveEmployeesUseCase(employeeRepository)
    }

    val addEmployeeUseCase: AddEmployeeUseCase by lazy {
        AddEmployeeUseCase(employeeRepository)
    }

    val observeEpiTypesUseCase: ObserveEpiTypesUseCase by lazy {
        ObserveEpiTypesUseCase(epiTypeRepository)
    }

    val addEpiTypeUseCase: AddEpiTypeUseCase by lazy {
        AddEpiTypeUseCase(epiTypeRepository)
    }

    val observeDeliveriesUseCase: ObserveDeliveriesUseCase by lazy {
        ObserveDeliveriesUseCase(deliveryRepository)
    }

    val registerDeliveryUseCase: RegisterDeliveryUseCase by lazy {
        RegisterDeliveryUseCase(deliveryRepository, epiTypeRepository)
    }

    companion object {
        private const val DATABASE_NAME = "epi_offline_db"
    }
}

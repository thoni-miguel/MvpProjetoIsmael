package com.thoni.mvpprojetoismael.core.di

import android.content.Context
import androidx.room.Room
import com.thoni.mvpprojetoismael.core.database.AppDatabase
import com.thoni.mvpprojetoismael.data.repository.EmployeeRepositoryImpl
import com.thoni.mvpprojetoismael.domain.repository.EmployeeRepository
import com.thoni.mvpprojetoismael.domain.usecase.AddEmployeeUseCase
import com.thoni.mvpprojetoismael.domain.usecase.ObserveEmployeesUseCase

class AppContainer(context: Context) {
    private val database: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val employeeRepository: EmployeeRepository by lazy {
        EmployeeRepositoryImpl(database.employeesDao())
    }

    val observeEmployeesUseCase: ObserveEmployeesUseCase by lazy {
        ObserveEmployeesUseCase(employeeRepository)
    }

    val addEmployeeUseCase: AddEmployeeUseCase by lazy {
        AddEmployeeUseCase(employeeRepository)
    }

    companion object {
        private const val DATABASE_NAME = "epi_offline_db"
    }
}

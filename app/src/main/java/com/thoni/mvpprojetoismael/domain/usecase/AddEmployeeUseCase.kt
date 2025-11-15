package com.thoni.mvpprojetoismael.domain.usecase

import com.thoni.mvpprojetoismael.domain.model.Employee
import com.thoni.mvpprojetoismael.domain.repository.EmployeeRepository
import java.util.UUID

class AddEmployeeUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(name: String, sector: String?): Result<Unit> {
        if (name.isBlank()) {
            return Result.failure(IllegalArgumentException("Nome do funcionário é obrigatório"))
        }

        val now = System.currentTimeMillis()
        val employee = Employee(
            id = UUID.randomUUID().toString(),
            name = name.trim(),
            sector = sector?.takeIf { it.isNotBlank() }?.trim(),
            role = null,
            shoeSize = null,
            uniformSize = null,
            active = true,
            synced = false,
            updatedAt = now
        )
        return runCatching {
            repository.addOrUpdate(employee)
        }
    }
}


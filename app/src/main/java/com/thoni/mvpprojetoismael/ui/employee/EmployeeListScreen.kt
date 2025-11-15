package com.thoni.mvpprojetoismael.ui.employee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thoni.mvpprojetoismael.domain.model.Employee

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeListScreen(
    state: EmployeeListUiState,
    onAddEmployee: (String, String?) -> Unit,
    onErrorShown: () -> Unit,
    onInputsCleared: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Funcionários") })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        EmployeeSection(
            state = state,
            snackbarHostState = snackbarHostState,
            onAddEmployee = onAddEmployee,
            onErrorShown = onErrorShown,
            onInputsCleared = onInputsCleared,
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        )
    }
}

@Composable
fun EmployeeSection(
    state: EmployeeListUiState,
    snackbarHostState: SnackbarHostState,
    onAddEmployee: (String, String?) -> Unit,
    onErrorShown: () -> Unit,
    onInputsCleared: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (name, setName) = remember { mutableStateOf("") }
    val (sector, setSector) = remember { mutableStateOf("") }

    LaunchedEffect(state.errorMessage) {
        val error = state.errorMessage
        if (error != null) {
            snackbarHostState.showSnackbar(
                message = error,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            onErrorShown()
        }
    }

    LaunchedEffect(state.shouldClearInput) {
        if (state.shouldClearInput) {
            setName("")
            setSector("")
            onInputsCleared()
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Funcionários",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = name,
            onValueChange = setName,
            label = { Text("Nome") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = sector,
            onValueChange = setSector,
            label = { Text("Setor (opcional)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { onAddEmployee(name, sector) },
            enabled = name.isNotBlank() && !state.isSaving,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Adicionar")
        }

        if (state.isSaving) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (state.isLoading && state.employees.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            EmployeeList(
                employees = state.employees,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun EmployeeList(
    employees: List<Employee>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        employees.forEach { employee ->
            EmployeeItem(employee)
        }
    }
}

@Composable
private fun EmployeeItem(employee: Employee) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = employee.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            employee.sector?.let {
                Text(text = "Setor: $it", style = MaterialTheme.typography.bodyMedium)
            }
            Text(
                text = if (employee.synced) "Sincronizado" else "Pendente",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

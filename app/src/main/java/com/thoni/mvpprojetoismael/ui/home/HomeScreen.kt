package com.thoni.mvpprojetoismael.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thoni.mvpprojetoismael.ui.employee.EmployeeListUiState
import com.thoni.mvpprojetoismael.ui.employee.EmployeeSection
import com.thoni.mvpprojetoismael.ui.epitype.EpiTypeListUiState
import com.thoni.mvpprojetoismael.ui.epitype.EpiTypeSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    employeeState: EmployeeListUiState,
    epiTypeState: EpiTypeListUiState,
    onAddEmployee: (String, String?) -> Unit,
    onEmployeeErrorShown: () -> Unit,
    onEmployeeInputsCleared: () -> Unit,
    onAddEpiType: (String, String) -> Unit,
    onEpiTypeErrorShown: () -> Unit,
    onEpiTypeInputsCleared: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Cadastros básicos") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            EmployeeSection(
                state = employeeState,
                snackbarHostState = snackbarHostState,
                onAddEmployee = onAddEmployee,
                onErrorShown = onEmployeeErrorShown,
                onInputsCleared = onEmployeeInputsCleared,
                modifier = Modifier.fillMaxWidth()
            )
            EpiTypeSection(
                state = epiTypeState,
                snackbarHostState = snackbarHostState,
                onAddEpiType = onAddEpiType,
                onErrorShown = onEpiTypeErrorShown,
                onInputsCleared = onEpiTypeInputsCleared,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

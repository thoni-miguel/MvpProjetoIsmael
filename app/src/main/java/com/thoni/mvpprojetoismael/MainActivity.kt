package com.thoni.mvpprojetoismael

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thoni.mvpprojetoismael.ui.theme.MvpProjetoIsmaelTheme
import com.thoni.mvpprojetoismael.ui.employee.EmployeeListScreen
import com.thoni.mvpprojetoismael.ui.employee.EmployeeListViewModel
import com.thoni.mvpprojetoismael.ui.employee.EmployeeListViewModelFactory

class MainActivity : ComponentActivity() {
    private val appContainer by lazy { (application as MvpProjetoIsmaelApp).appContainer }
    private val viewModel: EmployeeListViewModel by viewModels {
        EmployeeListViewModelFactory(
            appContainer.observeEmployeesUseCase,
            appContainer.addEmployeeUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MvpProjetoIsmaelTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    EmployeeListScreen(
                        state = state,
                        onAddEmployee = viewModel::onAddEmployee,
                        onErrorShown = viewModel::onErrorShown,
                        onInputsCleared = viewModel::onInputsCleared
                    )
                }
            }
        }
    }
}

package com.tsquaredapps.employeedirectory.directory

import androidx.lifecycle.viewModelScope
import com.tsquaredapps.employeedirectory.common.BaseStateViewModel
import com.tsquaredapps.employeedirectory.directory.DirectoryState.*
import com.tsquaredapps.employeedirectory.directory.adapter.LetterHeader
import com.tsquaredapps.employeedirectory.model.Employee
import com.tsquaredapps.employeedirectory.repository.EmployeeApi
import com.tsquaredapps.employeedirectory.repository.Failure
import com.tsquaredapps.employeedirectory.repository.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DirectoryViewModel
@Inject constructor(private val employeeApi: EmployeeApi) : BaseStateViewModel<DirectoryState>() {

    fun start() {
        viewModelScope.launch(Dispatchers.Main) {
            when (val result = employeeApi.getEmployees()) {
                is Success -> with(result.data) {
                    state.value = if (isEmpty()) ShowEmptyList
                    else ShowEmployees(createEmployeeLetterMap())
                }
                is Failure -> state.value = ShowError
            }
        }
    }

    private fun List<Employee>.createEmployeeLetterMap(): List<Pair<LetterHeader, List<Employee>>> =
        asSequence()
            .sortedBy { it.fullName }
            .groupBy { it.fullName.first().toUpperCase() }
            .map { LetterHeader(it.key) to it.value }

    fun onRetryClicked() {
        state.value = ShowLoader
        start()
    }
}

sealed class DirectoryState {
    class ShowEmployees(val employees: List<Pair<LetterHeader, List<Employee>>>) : DirectoryState()
    object ShowEmptyList : DirectoryState()
    object ShowError : DirectoryState()
    object ShowLoader : DirectoryState()
}
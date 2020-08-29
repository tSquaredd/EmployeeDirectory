package com.tsquaredapps.employeedirectory.directory

import android.util.MalformedJsonException
import com.tsquaredapps.employeedirectory.common.BaseCoroutineViewModelTest
import com.tsquaredapps.employeedirectory.directory.DirectoryState.*
import com.tsquaredapps.employeedirectory.ext.assertStateOrder
import com.tsquaredapps.employeedirectory.repository.EmployeeApi
import com.tsquaredapps.employeedirectory.repository.Failure
import com.tsquaredapps.employeedirectory.repository.Success
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DirectoryViewModelTest : BaseCoroutineViewModelTest<DirectoryState>() {

    private lateinit var viewModel: DirectoryViewModel
    private val employeeApi: EmployeeApi = mockk()

    @BeforeEach
    fun beforeEach() {
        viewModel = DirectoryViewModel(employeeApi).apply {
            stateLiveData.observeForever(stateObserver)
        }
    }

    @Test
    fun `given api returns employee list, when view model started, then set state to ShowEmployees`() {
        coEvery { employeeApi.getEmployees() } returns Success(listOf(mockk(), mockk()))

        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is ShowEmployees }
    }

    @Test
    fun `given api returns empty list, when view model started, then set state to ShowEmptyList`() {
        coEvery { employeeApi.getEmployees() } returns Success(emptyList())

        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is ShowEmptyList }
    }

    @Test
    fun `given api fails, when view model started, then set state to ShowError`() {
        coEvery { employeeApi.getEmployees() } returns Failure(MalformedJsonException(""))

        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is ShowError }
    }

    @Test
    fun `given api fails, when employee retries, then show loader and try again`() {
        coEvery { employeeApi.getEmployees() } returns Success(listOf(mockk(), mockk()))

        viewModel.onRetryClicked()

        verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(ShowLoader::class, ShowEmployees::class)
    }
}
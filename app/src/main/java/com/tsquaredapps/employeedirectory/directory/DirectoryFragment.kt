package com.tsquaredapps.employeedirectory.directory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tsquaredapps.employeedirectory.R
import com.tsquaredapps.employeedirectory.common.BaseFragment
import com.tsquaredapps.employeedirectory.databinding.FragmentDirectoryBinding
import com.tsquaredapps.employeedirectory.directory.AlternateView.EMPTY
import com.tsquaredapps.employeedirectory.directory.AlternateView.ERROR
import com.tsquaredapps.employeedirectory.directory.DirectoryState.*
import com.tsquaredapps.employeedirectory.ext.setAsGone
import com.tsquaredapps.employeedirectory.ext.setAsVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DirectoryFragment : BaseFragment<FragmentDirectoryBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DirectoryViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDirectoryBinding = FragmentDirectoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.retryButton.setOnClickListener {
            viewModel.onRetryClicked()
        }

        with(viewModel) {
            stateLiveData.observe(viewLifecycleOwner, ::onStateChanged)
            start()
        }
    }

    private fun onStateChanged(state: DirectoryState?) {
        when (state) {
            is ShowEmployees -> {
                with(binding) {
                    progressBar.setAsGone()
                    image.setAsGone()
                    message.setAsGone()
                    retryButton.setAsGone()
                }
            }
            is ShowEmptyList -> showAlternateView(EMPTY)
            is ShowError -> showAlternateView(ERROR)
            is ShowLoader -> binding.progressBar.setAsVisible()
        }
    }

    private fun showAlternateView(alternateView: AlternateView) {
        with(binding) {
            with(image) {
                setImageDrawable(
                    ContextCompat.getDrawable(context, alternateView.imageResource)
                )
                contentDescription =
                    getString(alternateView.imageContentDescription)
            }

            message.text = getString(alternateView.messageResource)
            progressBar.setAsGone()
            image.setAsVisible()
            message.setAsVisible()
            retryButton.setAsVisible()
        }
    }
}

enum class AlternateView(
    @DrawableRes val imageResource: Int,
    @StringRes val imageContentDescription: Int,
    @StringRes val messageResource: Int
) {
    ERROR(
        R.drawable.shocked_bird,
        R.string.employee_list_error_image_content_description,
        R.string.employee_retrieval_error_message
    ),
    EMPTY(
        R.drawable.llama,
        R.string.employee_list_empty_image_content_description,
        R.string.empty_employee_list_message
    )
}
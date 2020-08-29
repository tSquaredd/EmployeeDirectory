package com.tsquaredapps.employeedirectory.directory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tsquaredapps.employeedirectory.common.BaseFragment
import com.tsquaredapps.employeedirectory.databinding.FragmentDirectoryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DirectoryFragment : BaseFragment<FragmentDirectoryBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModelL: DirectoryViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDirectoryBinding = FragmentDirectoryBinding.inflate(inflater, container, false)
}
package com.tsquaredapps.employeedirectory.directory.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.tsquaredapps.employeedirectory.R
import com.tsquaredapps.employeedirectory.databinding.EmployeeItemBinding
import com.tsquaredapps.employeedirectory.databinding.LetterHeaderItemBinding
import com.tsquaredapps.employeedirectory.model.Employee
import java.lang.Exception

fun employeeAdapterDelegate(itemClickListener: (Employee) -> Unit) =
    adapterDelegateViewBinding<Employee, DirectoryScreenModel, EmployeeItemBinding>(
        { inflater, root -> EmployeeItemBinding.inflate(inflater, root, false) }) {

        bind {
            with(binding) {
                root.setOnClickListener { itemClickListener(item) }
                name.text = item.fullName
                team.text = item.team

                with(Picasso.get()) {
                    load(item.smallPhotoUrl)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.drawable.placeholder_person)
                        .error(R.drawable.placeholder_person)
                        .into(image, object : Callback {
                            override fun onSuccess() {
                                // no-op, loaded from cache
                            }

                            override fun onError(e: Exception?) {
                                load(item.smallPhotoUrl)
                                    .placeholder(R.drawable.placeholder_person)
                                    .error(R.drawable.placeholder_person)
                                    .into(image)
                            }
                        })
                }
            }
        }
    }

fun letterHeaderAdapterDelegate() =
    adapterDelegateViewBinding<LetterHeader, DirectoryScreenModel, LetterHeaderItemBinding>(
        { inflater, root -> LetterHeaderItemBinding.inflate(inflater, root, false) }) {
        bind {
            binding.letter.text = item.letter.toString()
        }
    }
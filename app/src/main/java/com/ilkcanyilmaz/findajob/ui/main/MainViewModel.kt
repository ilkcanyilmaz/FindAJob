package com.ilkcanyilmaz.findajob.ui.main

import com.ilkcanyilmaz.findajob.data.Constants
import com.ilkcanyilmaz.findajob.data.FirestoreRepository
import com.ilkcanyilmaz.findajob.data.Job
import com.ilkcanyilmaz.findajob.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: FirestoreRepository) :
    BaseViewModel() {

    private val _jobs = MutableStateFlow<List<Job>?>(null)
    val jobs: StateFlow<List<Job>?> = _jobs

    val errMsg: MutableStateFlow<String?> = MutableStateFlow(null)

    fun getJobs() {
        repository.getAllDocuments(Constants.JOB_COLLECTION)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _jobs.value = it.result.toObjects(Job::class.java)
                } else {
                    errMsg.value = it.exception?.message.toString()
                }
            }
    }
}
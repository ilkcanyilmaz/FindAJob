package com.ilkcanyilmaz.findajob.ui.newjob

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.ilkcanyilmaz.findajob.data.*
import com.ilkcanyilmaz.findajob.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewJobViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val authRepository: AuthRepository
) :
    BaseViewModel() {

    private val _isNewUserSuccess = MutableStateFlow<Boolean?>(null)
    val isNewUserSuccess: StateFlow<Boolean?> = _isNewUserSuccess

    private val _errMsg = MutableStateFlow<String?>(null)
    val errMsg: StateFlow<String?> = _errMsg

    fun addJobFirestore(job: Job) {
        firestoreRepository.addDocument(job, Constants.JOB_COLLECTION)
            .addOnSuccessListener {
                _isNewUserSuccess.value = true
            }.addOnFailureListener {
                _errMsg.value = it.message
            }
    }
}
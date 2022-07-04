package com.example.andrewmoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.andrewmoney.data.local.model.LocalHistoryModel
import com.example.andrewmoney.data.local.model.LocalVaultModel
import com.example.andrewmoney.data.repository.AppRepository
import kotlinx.coroutines.launch

class AppViewModel (private val repository: AppRepository) : ViewModel() {

    // VAULT

    suspend fun getLatestVault(): LiveData<List<LocalVaultModel>> {
        repository.getLatestVaults()
        return repository.vaults
    }

    suspend fun likeVault(name: String){
        repository.likeVault(name)
    }

    suspend fun dislikeVault(name: String){
        repository.dislikeVault(name)
    }

    // HISTORY

    suspend fun getHistory(): LiveData<List<LocalHistoryModel>> {
        repository.getHistory()
        return repository.history
    }

    suspend fun addHistory(item: LocalHistoryModel){
        repository.addHistory(item)
    }

}
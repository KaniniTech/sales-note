package com.kaninitech.salesnote.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaninitech.salesnote.data.datastore.BusinessDetPrefs
import com.kaninitech.salesnote.data.datastore.UserBusinessData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BusinessDetPrefsViewModel(
    private val businessDetPrefs: BusinessDetPrefs
) : ViewModel() {

    // Expose business data as StateFlow (for reactive UI)
    val userBusinessData: StateFlow<UserBusinessData> = businessDetPrefs.userBizPrefData
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserBusinessData(
                isDataSet = false,
                businessName = "",
                businessAddress = "",
                businessId = ""
            )
        )

    // Save business data
    fun saveBusinessData(name: String, address: String, businessId: String) {
        viewModelScope.launch {
            businessDetPrefs.saveUserBizPref(name, address, businessId)
        }
    }

    // Clear all business data
    fun clearBusinessData() {
        viewModelScope.launch {
            businessDetPrefs.clearUserData()
        }
    }
}

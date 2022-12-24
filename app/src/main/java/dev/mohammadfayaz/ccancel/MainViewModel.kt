package dev.mohammadfayaz.ccancel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
  private val viewState = MutableStateFlow(initialState())
  val state = viewState.asStateFlow()

  private val apiCall = FakeAPI.getInstance()
  private var job: Job? = null

  private fun initialState(): MainActivityState {
    return MainActivityState(
      city = "",
      pincode = "",
      loading = false,
      city2 = "",
      pincode2 = "",
      loading2 = false,
    )
  }

  fun updatePincode(value: String) {
    viewState.value = viewState.value.copy(
      pincode = filterPincode(value)
    )
  }

  private fun filterPincode(value: String): String {
    var filtered = value.filter { it.isDigit() }
    if (filtered.length > 6) {
      filtered = filtered.substring(0, 6)
    }
    return filtered
  }

  fun updatePincode2(value: String) {
    viewState.value = viewState.value.copy(
      pincode2 = filterPincode(value)
    )
  }

  fun search() {
    if (job?.isActive == true) {
      Log.d("coroutine", "Job is active, cancelling previous job")
      job?.cancel()
    }
    job = viewModelScope.launch {
      viewState.value = viewState.value.copy(
        loading = true,
        city = ""
      )
      val result = apiCall.invoke(viewState.value.pincode)
      viewState.value = viewState.value.copy(
        city = result ?: "Not found",
        loading = false
      )
    }
  }

  fun searchWithoutCancellation() {
    viewModelScope.launch {
      viewState.value = viewState.value.copy(
        loading2 = true,
        city2 = ""
      )
      val result = apiCall.searchWithRandomDelay(viewState.value.pincode2)
      viewState.value = viewState.value.copy(
        city2 = result ?: "Not found",
        loading2 = false
      )
    }
  }
}

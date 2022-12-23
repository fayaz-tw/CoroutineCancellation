package dev.mohammadfayaz.ccancel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
  private val viewState = MutableStateFlow(initialState())
  val state = viewState.asStateFlow()

  private fun initialState(): MainActivityState {
    return MainActivityState(
      city = "",
      pincode = "",
      loading = false
    )
  }

  fun updatePincode(value: String) {
    var filtered = value.filter { it.isDigit() }
    if (filtered.length > 6) {
      filtered = filtered.substring(0, 6)
    }
    viewState.value = viewState.value.copy(
      pincode = filtered
    )
  }

  fun search() {
    viewModelScope.launch {
      viewState.value = viewState.value.copy(
        loading = true,
        city = ""
      )
      delay(3000)
      viewState.value = viewState.value.copy(
        city = "Mock city",
        loading = false
      )
    }
  }
}

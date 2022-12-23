package dev.mohammadfayaz.ccancel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mohammadfayaz.ccancel.ui.theme.CoroutineCancellationTheme

class MainActivity : ComponentActivity() {

  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    viewModel = MainViewModel()

    setContent {
      Content(viewModel)
    }
  }
}

@Preview
@Composable
private fun Preview() {
  Content(MainViewModel())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(viewModel: MainViewModel) {
  val state = viewModel.state.collectAsState().value
  CoroutineCancellationTheme {
    // A surface container using the 'background' color from the theme
    Surface(
      modifier = Modifier.fillMaxSize(),
      color = MaterialTheme.colorScheme.background
    ) {
      Column(
        modifier = Modifier.padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        TextField(
          value = state.pincode,
          onValueChange = {
            viewModel.updatePincode(it)
          },
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
          ),
          trailingIcon = {
            AnimatedVisibility(visible = state.loading) {
              CircularProgressIndicator(
                modifier = Modifier.size(32.dp)
              )
            }
          }
        )
        Text(
          modifier = Modifier.padding(vertical = 8.dp),
          text = state.city
        )
        Button(onClick = { viewModel.search() }) {
          Text(text = "Search")
        }
      }
    }
  }
}

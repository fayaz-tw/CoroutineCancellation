package dev.mohammadfayaz.ccancel

import java.io.IOException
import java.io.InputStream
import kotlin.random.Random
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONObject

class FakeAPI private constructor() {

  private val citiesData = HashMap<String, String>()

  suspend fun loadData(inputStream: InputStream) {
    withContext(Dispatchers.IO) {
      try {
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val jsonObject = JSONObject(String(buffer))
        jsonObject.keys().forEach { key ->
          citiesData[key] = jsonObject[key] as String
        }
      } catch (ex: IOException) {
        throw ex
      }
    }
  }

  suspend operator fun invoke(pincode: String): String? {
    delay(3000)
    return citiesData[pincode]
  }

  suspend fun searchWithRandomDelay(pincode: String): String? {
    val random = Random.nextInt(2,6)
    delay(random * 1000L)
    return citiesData[pincode]
  }

  companion object {
    private val instance = FakeAPI()

    fun getInstance() = instance
  }
}

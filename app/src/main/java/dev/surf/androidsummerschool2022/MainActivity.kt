package dev.surf.androidsummerschool2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import dev.surf.androidsummerschool2022.api.CatApi
import dev.surf.androidsummerschool2022.api.CatResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TAG = "coroutine"

class MainActivity : AppCompatActivity() {

    private val sharedFlow = MutableSharedFlow<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.hello_tv).setOnClickListener {
            lifecycleScope.launch {
                sharedFlow.emit(Unit)
            }
        }

        val catApi = CatApi.create()

        lifecycleScope.launch {
            listOf(
                async {
                    catApi.getRandomCat("cute")
                }.await(),
                async {
                    catApi.getRandomCat("funny")
                }.await()
            ).forEach {
                Log.d(TAG, "$it")
            }
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "thread: ${Thread.currentThread().name}")
                val cat = catApi.getRandomCatBlocking("cute").execute().body()
                Log.d(TAG, "blocking cat: $cat")
            }
        }

        lifecycleScope.launch {
            sharedFlow.flatMapLatest {
                flow<CatResponse> {
                    catApi.getRandomCat("funny")
                }
            }.collect {
                Log.d(TAG, "$it")
            }
        }
    }
}
package dev.surf.androidsummerschool2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import dev.surf.androidsummerschool2022.api.CatApi
import dev.surf.androidsummerschool2022.api.CatResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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
                async { catApi.getRandomCat("cute") }.await(),
                async { catApi.getRandomCat("funny") }.await()
            ).forEach {
                Log.d("onCreate", "$it")
            }
        }

        lifecycleScope.launch {
            sharedFlow.flatMapLatest {
                flow<CatResponse> {
                    catApi.getRandomCat("funny")
                }
            }.collect {
                Log.d("onCreate", "$it")
            }
        }
    }
}
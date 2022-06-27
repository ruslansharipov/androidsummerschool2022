package dev.surf.androidsummerschool2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import dev.surf.androidsummerschool2022.api.CatApi
import dev.surf.androidsummerschool2022.api.CatResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catApi = CatApi.create()

        lifecycleScope.launch {
            listOf(
                async { catApi.getRandomCat("cute") }.await(),
                async { catApi.getRandomCat("funny") }.await()
            ).forEach {
                Log.d("onCreate", "$it")
            }
        }
    }
}
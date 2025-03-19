package dam2.m08.chucknorrisapi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dam2.m08.chucknorrisapi.API.retrofitService
import dam2.m08.chucknorrisapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }
    lateinit var finalJoke: Joke

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        getLanguages()
    }

    private fun initListener() {
        binding.btGetJoke.setOnClickListener {
            getJoke()
        }
    }

    private fun getJoke() {
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            val result = retrofitService.getJoke()
            if(result.isSuccessful){
                checkResult(result.body())
            }else{
                showError()
            }
        }
    }

    private fun checkResult(joke: Joke?) {
        if(joke != null){
            val jokeValue = joke.value

            if(jokeValue.isNotEmpty()){

                runOnUiThread {
                    binding.tvJoke.text = jokeValue
                }
            }
        }
    }

    private fun getLanguages() {
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {

            val joke = retrofitService.getJoke()

            if(joke.isSuccessful){
                finalJoke = (joke.body() ?: null)!!
                showSuccess()
            }else{
                showError()
            }
        }
    }

    private fun showSuccess() {
        runOnUiThread{
            Toast.makeText(this, "Succesful get", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showError() {
        runOnUiThread{
            Toast.makeText(this, "Error get", Toast.LENGTH_SHORT).show()
        }
    }
}
package dam2.m08.chucknorrisapi

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService{
    @GET ("/jokes/random")
    suspend fun getJoke(): Response<Joke>
}
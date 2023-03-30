package ale.ricardo.githubapp.network

import ale.ricardo.githubapp.model.SearchRepository
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/search/repositories")
    suspend fun searchRepositories(@Query("q") key: String = "Android",@Query("page") pageIndex:Int,@Query("per_page") pageSize:Int):SearchRepository
}
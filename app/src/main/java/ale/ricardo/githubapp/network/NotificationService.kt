package ale.ricardo.githubapp.network

import ale.ricardo.githubapp.model.CommitModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationService {
    @GET("/repos/aleyyds/GithubApp/commits")
    suspend fun fetchAllCommit(@Query("page") pageIndex:Int,@Query("per_page") pageSize:Int): List<CommitModel>

}
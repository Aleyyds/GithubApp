package ale.ricardo.githubapp.network

import ale.ricardo.githubapp.model.Oauth2Response
import ale.ricardo.githubapp.model.Repository
import ale.ricardo.githubapp.model.UserModel
import retrofit2.http.*

interface UserService {
    @GET("/user")
    suspend fun fetchUserInfo():UserModel

    @GET("/users/Aleyyds/repos")
    suspend fun fetchUserRepository(@Query("page") pageIndex:Int,@Query("per_page") pageSize:Int):List<Repository>
}
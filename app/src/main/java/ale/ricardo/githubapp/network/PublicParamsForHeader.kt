package ale.ricardo.githubapp.network

import ale.ricardo.githubapp.common.ACCESS_TOKEN
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class PublicParamsForHeader : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/vnd.github+json")
            .addHeader("Authorization", "Bearer $ACCESS_TOKEN")
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
            .build()
        return chain.proceed(addParam(request))
    }

    private fun addParam(oldRequest: Request): Request {

        val builder: HttpUrl.Builder = oldRequest.url
            .newBuilder()

        val newRequest: Request = oldRequest.newBuilder()
            .method(oldRequest.method, oldRequest.body)
            .url(builder.build())
            .build()

        return newRequest
    }

}
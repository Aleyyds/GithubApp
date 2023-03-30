package ale.ricardo.githubapp.model

import com.google.gson.annotations.SerializedName

data class Oauth2Response(

  @SerializedName("device_code")  val deviceCode: String,
  @SerializedName("expires_in")  val expiresIn: Int,
    val interval: Int,
   @SerializedName("user_code") val userCode: String,
  @SerializedName("verification_uri")  val verificationUri: String
)
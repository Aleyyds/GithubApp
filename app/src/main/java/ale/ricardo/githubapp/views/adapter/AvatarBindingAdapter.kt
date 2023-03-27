package ale.ricardo.githubapp.views.CustomView

import ale.ricardo.githubapp.R
import ale.ricardo.githubapp.common.TAG
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.CachePolicy
import coil.size.Scale
import coil.transform.CircleCropTransformation


@BindingAdapter(value = ["bindingAvatar"])
fun bindingAvatar(imageView: ImageView, url: String?) {
    Log.d(TAG, "userAvatarUrl----->:  $url")
    imageView.load(url) {
        crossfade(false)  //淡出淡出
        placeholder(R.mipmap.ic_launcher_round)
            transformations(CircleCropTransformation())
            scale(Scale.FILL)
        memoryCachePolicy(CachePolicy.ENABLED)//设置内存的缓存策略
        diskCachePolicy(CachePolicy.ENABLED)//设置磁盘的缓存策略
        networkCachePolicy(CachePolicy.ENABLED)//设置网络的缓存策略

    }
}
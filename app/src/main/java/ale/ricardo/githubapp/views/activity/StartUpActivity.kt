package ale.ricardo.githubapp.views.activity

import ale.ricardo.githubapp.R
import ale.ricardo.githubapp.databinding.ActivityStartUpBinding
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils

class StartUpActivity : AppCompatActivity() {

    private val activityStartUpBinding by lazy {
        ActivityStartUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityStartUpBinding.root)

        //隐藏appBar 和顶部顶部状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        //启动加载动画
        val splashLoadingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        activityStartUpBinding.tvAppName.animation = splashLoadingAnim

        //动画监听器
        splashLoadingAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
            }

            //动画结束之后跳转到另一个Activity
            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@StartUpActivity, MainActivity::class.java))
                    finish()
                },1000)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
    }
}
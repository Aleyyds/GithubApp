package ale.ricardo.githubapp.views.activity

import ale.ricardo.githubapp.R
import ale.ricardo.githubapp.common.TAG
import ale.ricardo.githubapp.databinding.ActivityMainBinding
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        navController = this.findNavController(R.id.nav_host_fragment_activity_main)

        navController.graph.forEach {
            Log.d(TAG, "onCreate: ${it.label}")
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //实现状态栏图标和文字颜色为暗色

        //设置底部状态栏动画效果
        val homeMotionLayout = findViewById<MotionLayout>(R.id.homeMotion)
        val notificationMotionLayout = findViewById<MotionLayout>(R.id.notificationMotion)
        val profileMotionLayout = findViewById<MotionLayout>(R.id.profileMotion)

        val destinationMap = mapOf(
            R.id.navigation_home to homeMotionLayout, R.id.navigation_notifications to notificationMotionLayout, R.id.navigation_profile to profileMotionLayout
        )

        destinationMap.forEach { map ->
            map.value.setOnClickListener {
                navController.navigate(map.key)
            }
        }


        navController.addOnDestinationChangedListener { controller, destination, _ ->
            controller.popBackStack() //清空返回栈
            //如果动画有延迟或者异常
            //统一将底部按钮归零
            destinationMap.values.forEach { v ->
                v.progress = 0f
            }
            destinationMap[destination.id]?.transitionToEnd()

            //隐藏导航栏
            when (destination.id) {
                R.id.searchFragment,R.id.searchResultFragment ,R.id.webViewActivity-> {
                    binding.bottomLinear.visibility = View.GONE
                }
                else ->{
                    binding.bottomLinear.visibility = View.VISIBLE
                }
            }

        }
    }


}
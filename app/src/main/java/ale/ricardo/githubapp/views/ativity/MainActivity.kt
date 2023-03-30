package ale.ricardo.githubapp.views.ativity

import ale.ricardo.githubapp.R
import ale.ricardo.githubapp.databinding.ActivityMainBinding
import ale.ricardo.githubapp.databinding.FragmentHomeBinding
import ale.ricardo.githubapp.databinding.HomeIconLayoutBinding
import ale.ricardo.githubapp.databinding.NotificationIconLayoutBinding
import ale.ricardo.githubapp.databinding.ProfileIconLayoutBinding
import android.os.Bundle
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


        val homeMotionLayout = findViewById<MotionLayout>(R.id.homeMotion)
        val notificationMotionLayout = findViewById<MotionLayout>(R.id.notificationMotion)
        val profileMotionLayout = findViewById<MotionLayout>(R.id.profileMotion)

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //实现状态栏图标和文字颜色为暗色

        val destinationMap = mapOf(
            R.id.navigation_home to homeMotionLayout,
            R.id.navigation_notifications to notificationMotionLayout,
            R.id.navigation_profile to profileMotionLayout
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
        }
    }


}
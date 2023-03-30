package ale.ricardo.githubapp

import ale.ricardo.githubapp.database.HistoryDataBase
import android.app.Application
import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Room
import com.scwang.smartrefresh.header.PhoenixHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.*
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader

class App : Application() {
    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.primaryColor, android.R.color.white);//全局设置主题颜色
            ClassicsHeader(context)
        }

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f);
        }
    }








}
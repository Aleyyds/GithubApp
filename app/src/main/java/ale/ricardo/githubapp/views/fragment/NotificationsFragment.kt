package ale.ricardo.githubapp.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ale.ricardo.githubapp.databinding.FragmentNotificationsBinding
import ale.ricardo.githubapp.viewmodel.NotificationsViewModel
import ale.ricardo.githubapp.views.adapter.NotificationAdapter
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.flow.collectLatest

class NotificationsFragment : Fragment(), OnRefreshLoadMoreListener {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!
    private lateinit var notificationsViewModel: NotificationsViewModel

    private lateinit var adapter:NotificationAdapter


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = NotificationAdapter()
        binding.recycle.adapter = adapter

        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(this)

        lifecycleScope.launchWhenCreated {
            notificationsViewModel.fetchNotification().collectLatest {
                adapter.submitData(it)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        adapter.refresh()
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                if (it.refresh is LoadState.NotLoading){
                    refreshLayout.finishRefresh()
                }
            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                if (it.append is LoadState.NotLoading){
                    refreshLayout.finishLoadMore()
                }
            }
        }
    }
}
package ale.ricardo.githubapp.views.fragment

import ale.ricardo.githubapp.R
import ale.ricardo.githubapp.common.TAG
import ale.ricardo.githubapp.databinding.FragmentHomeBinding
import ale.ricardo.githubapp.viewmodel.HomeViewModel
import ale.ricardo.githubapp.views.adapter.HomeRecycleAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.SeekBarBindingAdapter.OnProgressChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : Fragment(), OnRefreshLoadMoreListener, View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recycleAdapter: HomeRecycleAdapter

    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleAdapter = HomeRecycleAdapter()

        binding.apply {

            recycle.adapter = recycleAdapter
            recycle.layoutManager = LinearLayoutManager(context)


            smartRefreshLayout.setOnRefreshLoadMoreListener(this@HomeFragment)
            floatingActionButton.setOnClickListener(this@HomeFragment)
            editText.setOnClickListener(this@HomeFragment)

        }


        lifecycleScope.launchWhenCreated {
            homeViewModel.queryRepositoryByKey().catch {
                Log.e(TAG, "onViewCreated: error:$it")
            }.collectLatest {
                recycleAdapter.submitData(it)

            }
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        recycleAdapter.refresh()
        lifecycleScope.launchWhenCreated {
            recycleAdapter.loadStateFlow.collectLatest {
                if (it.refresh is LoadState.NotLoading) {
                    refreshLayout.finishRefresh()
                }
            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        lifecycleScope.launchWhenCreated {
            recycleAdapter.loadStateFlow.collectLatest {
                if (it.refresh is LoadState.NotLoading) {
                    refreshLayout.finishLoadMore()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floatingActionButton -> {
                binding.apply {
                    recycle.scrollToPosition(0)
                    smartRefreshLayout.autoRefresh()
                }
            }
            R.id.editText ->{
                findNavController().navigate(R.id.searchFragment)
            }
        }
    }
}
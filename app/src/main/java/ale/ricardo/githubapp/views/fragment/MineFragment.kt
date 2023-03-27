package ale.ricardo.githubapp.views.fragment

import ale.ricardo.githubapp.databinding.FragmentMineBinding
import ale.ricardo.githubapp.views.adapter.ProfileRepositoryAdapter
import ale.ricardo.githubapp.viewmodel.MineViewModel
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.flow.collectLatest

class MineFragment : Fragment(), OnRefreshLoadMoreListener {

    private var _binding: FragmentMineBinding? = null
    private val mineViewModel: MineViewModel by viewModels()
    private lateinit var smartRefreshLayout: SmartRefreshLayout

    private val adapter: ProfileRepositoryAdapter by lazy { ProfileRepositoryAdapter() }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMineBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smartRefreshLayout = binding.smartRefreshLayout
        smartRefreshLayout.setOnRefreshLoadMoreListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.recycle.adapter = adapter
        binding.recycle.layoutManager = LinearLayoutManager(context)



        lifecycleScope.launchWhenCreated {
            mineViewModel.loadUserRepository().collectLatest {
                adapter.submitData(it)
            }
        }


        lifecycleScope.launchWhenCreated {
            mineViewModel.fetchUserMetaData().collectLatest {
                binding.user = it

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
                if (it.refresh is LoadState.NotLoading) {
                    smartRefreshLayout.finishRefresh()
                }
            }
        }

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                if (it.source.append is LoadState.NotLoading){
                    smartRefreshLayout.finishLoadMore()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}
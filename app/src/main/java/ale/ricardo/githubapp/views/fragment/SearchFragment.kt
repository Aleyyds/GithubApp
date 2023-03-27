package ale.ricardo.githubapp.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ale.ricardo.githubapp.R
import ale.ricardo.githubapp.common.TAG
import ale.ricardo.githubapp.databinding.FragmentSearchBinding
import ale.ricardo.githubapp.viewmodel.HistoryViewModel
import ale.ricardo.githubapp.views.adapter.HistoryListAdapter
import android.text.TextUtils
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchFragment : Fragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentSearchBinding

    private val historyViewModel by viewModels<HistoryViewModel>()

    private lateinit var adapter: HistoryListAdapter



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentSearchBinding.inflate(layoutInflater)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HistoryListAdapter()
        mBinding.recycle.adapter = adapter


        lifecycleScope.launchWhenCreated {
            historyViewModel.getAllSearchHistory().collectLatest {
                adapter.submitList(it)
            }
        }

        mBinding.apply {
            ivBack.setOnClickListener(this@SearchFragment)
            searchButton.setOnClickListener(this@SearchFragment)
            ivClearHistory.setOnClickListener(this@SearchFragment)
            tvClearHistory.setOnClickListener(this@SearchFragment)
        }


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_back ->{
                findNavController().navigate(R.id.action_searchFragment_to_navigation_home)
            }
            R.id.search_button ->{
                val key =mBinding.editText.text?.trim()
                Log.d(TAG, "onClick: $key")
                if (!TextUtils.isEmpty(key)) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        historyViewModel.insertHistory(key.toString())
                    }
                    val argument = Bundle()
                    argument.putString("key",key.toString())
                    findNavController().navigate(R.id.action_searchFragment_to_searchResultFragment,argument)
                }

            }

            R.id.iv_clear_history,R.id.tv_clear_history ->{
                lifecycleScope.launchWhenCreated {
                    historyViewModel.deleteAllSearchHistory()
                }
            }
        }
    }

}
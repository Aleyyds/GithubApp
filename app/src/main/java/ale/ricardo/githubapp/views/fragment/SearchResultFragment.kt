package ale.ricardo.githubapp.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ale.ricardo.githubapp.R
import ale.ricardo.githubapp.databinding.FragmentSearhResultBinding
import ale.ricardo.githubapp.viewmodel.HomeViewModel
import ale.ricardo.githubapp.views.adapter.HomeRecycleAdapter
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest

class SearchResultFragment : Fragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentSearhResultBinding
    private lateinit var recycleAdapter: HomeRecycleAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var navController:NavController

    override fun onStart() {
        super.onStart()
        navController = findNavController()
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentSearhResultBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val key = requireArguments().getString("key")
        key?.let {
            mBinding.editText.setText(key)
        }
        recycleAdapter = HomeRecycleAdapter()

        mBinding.apply {
            recycle.adapter = recycleAdapter
            editText.clearFocus()
            editText.setOnClickListener(this@SearchResultFragment)
            ivBack.setOnClickListener(this@SearchResultFragment)
        }


        lifecycleScope.launchWhenCreated {
            key?.let {
                homeViewModel.queryRepositoryByKey(it).collectLatest {
                    recycleAdapter.submitData(it)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_back ->{
                navController.navigate(R.id.searchFragment)
            }

            R.id.editText ->{
                mBinding.editText.setText("")
                navController.navigate(R.id.searchFragment)
            }
        }
    }

}
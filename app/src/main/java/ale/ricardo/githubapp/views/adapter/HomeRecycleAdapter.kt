package ale.ricardo.githubapp.views.adapter

import ale.ricardo.githubapp.R
import ale.ricardo.githubapp.common.Utils
import ale.ricardo.githubapp.databinding.HomeItemBinding
import ale.ricardo.githubapp.model.Repository
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class HomeRecycleAdapter : PagingDataAdapter<Repository, HomeRecycleAdapter.ViewHolder>(DiffCallback()) {
    class ViewHolder(val binding: HomeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = getItem(position)
        holder.binding.repos = repository

        holder.binding.tvHomeStar.text = repository?.stargazers_count?.let {
            if (it>=1000){
                Utils.getFormatNumber(it.toString(),"1000") + "k"
            }else{
                it.toString()
            }
        }

        holder.binding.tvHomeFork.text = repository?.forks_count?.let {
            if (it>=1000){
                Utils.getFormatNumber(it.toString(),"1000") + "k"
            }else{
                it.toString()
            }
        }


        holder.binding.owner = repository?.owner


        holder.binding.detailCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("html_url",repository?.html_url)
            holder.binding.root.findNavController().navigate(R.id.webViewActivity,bundle)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    class DiffCallback : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }
}
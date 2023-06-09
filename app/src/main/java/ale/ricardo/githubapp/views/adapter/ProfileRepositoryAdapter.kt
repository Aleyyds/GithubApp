package ale.ricardo.githubapp.views.adapter

import ale.ricardo.githubapp.databinding.ProfileReposCardBinding
import ale.ricardo.githubapp.model.Repository
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ProfileRepositoryAdapter :
    PagingDataAdapter<Repository, ProfileRepositoryAdapter.ViewHolder>(DiffCallback()) {
    class ViewHolder(val binding: ProfileReposCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = getItem(position)

        holder.binding.repository = repository
        holder.binding.owner = repository!!.owner

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ProfileReposCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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
package ale.ricardo.githubapp.views.adapter

import ale.ricardo.githubapp.R
import ale.ricardo.githubapp.database.SearchHistoryEntity
import ale.ricardo.githubapp.databinding.SearchHistoryRecycleItemBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class HistoryListAdapter() : ListAdapter<SearchHistoryEntity, HistoryListAdapter.ViewHolder>(DiffCallback()) {


    class ViewHolder(val binding: SearchHistoryRecycleItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchHistoryRecycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyEntity = getItem(position)
        holder.binding.apply {
            tvHistory.setText(historyEntity.key)
            tvHistory.setOnClickListener {
                val argument = Bundle()
                argument.putString("key",historyEntity.key)
                holder.binding.root.findNavController().navigate(R.id.searchResultFragment,argument)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<SearchHistoryEntity>() {
        override fun areItemsTheSame(oldItem: SearchHistoryEntity, newItem: SearchHistoryEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchHistoryEntity, newItem: SearchHistoryEntity): Boolean {
            return oldItem == newItem
        }

    }
}
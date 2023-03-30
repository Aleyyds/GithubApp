package ale.ricardo.githubapp.views.adapter

import ale.ricardo.githubapp.common.TAG
import ale.ricardo.githubapp.databinding.NotificationItemBinding
import ale.ricardo.githubapp.model.CommitModel
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter : PagingDataAdapter<CommitModel, NotificationAdapter.ViewHolder>(DiffCallback()) {
    class ViewHolder(val mBinding: NotificationItemBinding) : RecyclerView.ViewHolder(mBinding.root) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commitModel = getItem(position)
        holder.mBinding.apply {
            commit = commitModel?.commit
            author = commitModel?.author
            Log.d(TAG, "onBindViewHolder: ${commitModel?.commit?.author.toString().substring(0,10)}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class DiffCallback : DiffUtil.ItemCallback<CommitModel>() {
        override fun areItemsTheSame(oldItem: CommitModel, newItem: CommitModel): Boolean {
            return oldItem.sha == newItem.sha
        }

        override fun areContentsTheSame(oldItem: CommitModel, newItem: CommitModel): Boolean {
            return newItem == oldItem
        }
    }
}
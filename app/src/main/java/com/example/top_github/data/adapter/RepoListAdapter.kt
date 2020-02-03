package com.example.top_github.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.top_github.R
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.imageCaching.core.ImageManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.result_item.view.*
import javax.inject.Inject

class RepoListAdapter(
    private var repoList: List<TrendingRepos>,
    var itemClickListener: OnItemClickListener,
    var imageManager: ImageManager): RecyclerView.Adapter<RepoListAdapter.RepoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder =
        RepoItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.result_item, parent, false)
        )

    override fun getItemCount(): Int = repoList.size

    fun setItems(repoList: List<TrendingRepos>){
        this.repoList = repoList
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        repoList[position].avatar.let {
            imageManager.displayImage(repoList[position].avatar!!, holder.itemView.imageView, R.drawable.ic_adb_black_24dp)
        }

        holder.itemView.title.text = repoList[position].repo.name
        holder.itemView.desc.text = repoList[position].username

        holder.itemView.setOnClickListener {
            itemClickListener?.onResultItemClick(
                repoList[position],
                holder.itemView.imageView,
                holder.itemView.title,
                holder.itemView.desc)
        }
    }

    class RepoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onResultItemClick(repoDetails: TrendingRepos, imageView: ImageView, titleView: TextView, descView: TextView)
    }
}
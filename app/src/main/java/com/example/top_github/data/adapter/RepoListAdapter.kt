package com.example.top_github.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.top_github.R
import com.example.top_github.data.model.TrendingRepos
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.result_item.view.*

class RepoListAdapter(private var repoList: List<TrendingRepos>): RecyclerView.Adapter<RepoListAdapter.RepoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder =
        RepoItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.result_item, parent, false)
        )

    override fun getItemCount(): Int = repoList.size

    fun setItems(repoList: List<TrendingRepos>){
        this.repoList = repoList
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        if (repoList[position].avatar != null)
            Picasso.get().load(repoList[position].avatar).into(holder.itemView.imageView)
        holder.itemView.title.text = repoList[position].repo.name
        holder.itemView.desc.text = repoList[position].username

//        holder.itemView.setOnClickListener {
//            listener.onResultItemClick(pages[position].pageid.toString())
//        }
    }

    class RepoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
package com.example.top_github.data.model

import com.google.gson.annotations.SerializedName

data class TrendingRepos (

    @SerializedName("username") val username : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("url") val url : String,
    @SerializedName("avatar") val avatar : String,
    @SerializedName("repo") val repo : Repo
)
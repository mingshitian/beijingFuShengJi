package com.example.beijifushengji.api

class ProjectRepository : BaseRepository() {

    suspend fun getProjectTypeDetailList(title: String, link: String): Result<User> {
        return safeApiCall(call = {requestBlogTypeList(title, link)},errorMessage = "发生未知错误")
    }

    private suspend fun requestBlogTypeList(title: String, link: String) =
            executeResponse(WanRetrofitClient.service.shareArticle(title,link))

}
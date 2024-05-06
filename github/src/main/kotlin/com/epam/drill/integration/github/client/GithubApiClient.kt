package com.epam.drill.integration.github.client

interface GithubApiClient {
    suspend fun postPullRequestReport(
        repository: String,
        pullRequestId: Int,
        comment: String)
}
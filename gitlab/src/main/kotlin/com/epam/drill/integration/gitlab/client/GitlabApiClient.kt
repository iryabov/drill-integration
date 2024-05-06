package com.epam.drill.integration.gitlab.client

interface GitlabApiClient {
    suspend fun postMergeRequestReport(
        projectId: String,
        mergeRequestId: String,
        comment: String)
}
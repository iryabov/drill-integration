package com.epam.drill.integration.github.service

import com.epam.drill.integration.common.client.DrillApiClient
import com.epam.drill.integration.common.report.ReportGenerator
import com.epam.drill.integration.github.client.GithubApiClient

class GithubCiCdService(
    private val githubApiClient: GithubApiClient,
    private val drillApiClient: DrillApiClient,
    private val reportGenerator: ReportGenerator
) {
    suspend fun postPullRequestReport(
        githubRepository: String,
        githubPullRequestId: Int,
        drillGroupId: String,
        drillAgentId: String,
        sourceBranch: String,
        targetBranch: String
    ) {
        val metrics = drillApiClient.getDiffMetricsByBranches(
            drillGroupId,
            drillAgentId,
            sourceBranch,
            targetBranch
        )
        val comment = reportGenerator.getDiffSummaryReport(
            metrics
        )
        githubApiClient.postPullRequestReport(
            githubRepository,
            githubPullRequestId,
            comment
        )
    }

}
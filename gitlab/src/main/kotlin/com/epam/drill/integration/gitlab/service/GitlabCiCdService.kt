package com.epam.drill.integration.gitlab.service

import com.epam.drill.integration.common.client.DrillApiClient
import com.epam.drill.integration.common.report.ReportGenerator
import com.epam.drill.integration.gitlab.client.GitlabApiClient

class GitlabCiCdService(
    private val gitlabApiClient: GitlabApiClient,
    private val drillApiClient: DrillApiClient,
    private val reportGenerator: ReportGenerator
) {
    suspend fun postMergeRequestReport(
        gitlabProjectId: String,
        gitlabMergeRequestId: String,
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
        gitlabApiClient.postMergeRequestReport(
            gitlabProjectId,
            gitlabMergeRequestId,
            comment
        )
    }

}
package com.epam.drill.integration.gradle

import com.epam.drill.integration.common.client.impl.DrillApiClientImpl
import com.epam.drill.integration.common.report.impl.TextReportGenerator
import com.epam.drill.integration.gitlab.client.impl.GitlabApiClientV4Impl
import com.epam.drill.integration.gitlab.service.GitlabCiCdService
import kotlinx.coroutines.runBlocking
import org.gradle.api.Task

fun Task.drillGitlabMergeRequestReportTask(ciCd: DrillCiCdProperties) {
    doFirst {
        val gitlabCiCdService = GitlabCiCdService(
            GitlabApiClientV4Impl(ciCd.gitlab.gitlabApiUrl!!, ciCd.gitlab.gitlabPrivateToken),
            DrillApiClientImpl(ciCd.drillApiUrl!!, ciCd.drillApiKey),
            TextReportGenerator()
        )
        runBlocking {
            gitlabCiCdService.postMergeRequestReport(
                gitlabProjectId = ciCd.gitlab.projectId!!,
                gitlabMergeRequestId = ciCd.gitlab.mergeRequestId!!,
                drillGroupId = ciCd.drillGroupId!!,
                drillAgentId = ciCd.drillAgentId!!,
                sourceBranch = ciCd.sourceBranch!!,
                targetBranch = ciCd.targetBranch!!,
                latestCommitSha = ciCd.latestCommitSha!!)
        }
    }
}
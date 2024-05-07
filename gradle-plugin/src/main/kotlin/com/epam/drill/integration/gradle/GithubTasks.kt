package com.epam.drill.integration.gradle

import com.epam.drill.integration.common.client.impl.DrillApiClientImpl
import com.epam.drill.integration.common.report.impl.TextReportGenerator
import com.epam.drill.integration.github.client.impl.GithubApiClientImpl
import com.epam.drill.integration.github.service.GithubCiCdService
import kotlinx.coroutines.runBlocking
import org.gradle.api.Task

fun Task.drillGithubPullRequestReport(ciCd: DrillCiCdProperties) {
    doFirst {
        val githubCiCdService = GithubCiCdService(
            GithubApiClientImpl(ciCd.github.githubApiUrl!!, ciCd.github.githubToken!!),
            DrillApiClientImpl(ciCd.drillApiUrl!!, ciCd.drillApiKey),
            TextReportGenerator()
        )
        runBlocking {
            githubCiCdService.postPullRequestReport(
                githubRepository = ciCd.github.githubRepository!!,
                githubPullRequestId = ciCd.github.pullRequestId!!,
                drillGroupId = ciCd.drillGroupId!!,
                drillAgentId = ciCd.drillAgentId!!,
                sourceBranch = ciCd.sourceBranch!!,
                targetBranch = ciCd.targetBranch!!,
                latestCommitSha = ciCd.latestCommitSha!!)
        }
    }
}
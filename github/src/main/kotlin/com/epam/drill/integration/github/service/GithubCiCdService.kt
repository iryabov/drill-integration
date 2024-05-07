/**
 * Copyright 2020 - 2022 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        targetBranch: String,
        latestCommitSha: String
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
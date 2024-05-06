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
package com.epam.drill.integration.gradle

import com.epam.drill.integration.common.client.impl.DrillApiClientImpl
import com.epam.drill.integration.common.report.impl.TextReportGenerator
import com.epam.drill.integration.github.client.impl.GithubApiClientImpl
import com.epam.drill.integration.github.service.GithubCiCdService
import com.epam.drill.integration.gitlab.client.impl.GitlabApiClientV4Impl
import com.epam.drill.integration.gitlab.service.GitlabCiCdService
import org.gradle.api.Plugin
import org.gradle.api.Project
import kotlinx.coroutines.runBlocking

class DrillCiCdIntegrationGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val ciCd = project.extensions.create("drillCiCd", DrillCiCdProperties::class.java)
        val drillApiClient = DrillApiClientImpl(ciCd.drillApiUrl!!, ciCd.drillApiKey)
        val reportGenerator = TextReportGenerator()

        project.task("drillGitlabMergeRequestReport") {
            doFirst {
                val gitlabCiCdService = GitlabCiCdService(
                    GitlabApiClientV4Impl(ciCd.gitlab.gitlabApiUrl!!, ciCd.gitlab.gitlabPrivateToken),
                    drillApiClient,
                    reportGenerator
                )
                runBlocking {
                    gitlabCiCdService.postMergeRequestReport(
                        gitlabProjectId = ciCd.gitlab.projectId!!,
                        gitlabMergeRequestId = ciCd.gitlab.mergeRequestId!!,
                        drillGroupId = ciCd.drillGroupId!!,
                        drillAgentId = ciCd.drillAgentId!!,
                        sourceBranch = ciCd.sourceBranch!!,
                        targetBranch = ciCd.targetBranch!!)
                }
            }
        }

        project.task("drillGithubPullRequestReport") {
            doFirst {
                val githubCiCdService = GithubCiCdService(
                    GithubApiClientImpl(ciCd.github.githubApiUrl!!, ciCd.github.githubToken!!),
                    drillApiClient,
                    reportGenerator
                )
                runBlocking {
                    githubCiCdService.postPullRequestReport(
                        githubRepository = ciCd.github.githubRepository!!,
                        githubPullRequestId = ciCd.github.pullRequestId!!,
                        drillGroupId = ciCd.drillGroupId!!,
                        drillAgentId = ciCd.drillAgentId!!,
                        sourceBranch = ciCd.sourceBranch!!,
                        targetBranch = ciCd.targetBranch!!)
                }
            }
        }
    }
}



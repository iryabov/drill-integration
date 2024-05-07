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
package com.epam.drill.integration.cli

import com.epam.drill.integration.common.client.impl.DrillApiClientImpl
import com.epam.drill.integration.common.report.impl.TextReportGenerator
import com.epam.drill.integration.github.client.impl.GithubApiClientImpl
import com.epam.drill.integration.github.service.GithubCiCdService
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.runBlocking

class GithubPullRequestReportCommand: CliktCommand(name = "githubPullRequestReport") {
    private val drillApiUrl by option("-drill-u", "--drillApiUrl").required()
    private val drillApiKey by option("-drill-k", "--drillApiKey")
    private val drillGroupId by option("-g", "--drillGroupId").required()
    private val drillAgentId by option("-a", "--drillAgentId").required()
    private val sourceBranch by option("-sb", "--sourceBranch").required()
    private val targetBranch by option("-tb", "--targetBranch").required()
    private val latestCommitSha by option("-lc", "--latestCommitSha").required()
    private val githubApiUrl by option("-gh-u", "--githubApiUrl").default("https://api.github.com")
    private val githubToken by option("-gh-t", "--githubToken").required()
    private val githubRepository by option("-r", "--githubRepository").required()
    private val githubPullRequestNumber by option("-pr", "--githubPullRequestNumber").int().required()

    override fun run() {
        echo("Posting Drill4J Pull Request Report to GitHub...")
        val githubCiCdService = GithubCiCdService(
            GithubApiClientImpl(githubApiUrl, githubToken),
            DrillApiClientImpl(drillApiUrl, drillApiKey),
            TextReportGenerator()
        )
        runBlocking {
            githubCiCdService.postPullRequestReport(
                githubRepository,
                githubPullRequestNumber,
                drillGroupId,
                drillAgentId,
                sourceBranch,
                targetBranch,
                latestCommitSha
            )
        }
        echo("Done.")
    }
}
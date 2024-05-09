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
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import kotlinx.coroutines.runBlocking
import java.io.File

class GithubPullRequestReportByEventCommand: CliktCommand(name = "githubPullRequestReportByEvent") {
    private val drillApiUrl by option("-drill-u", "--drillApiUrl", envvar = "GITHUB_API_URL").required()
    private val drillApiKey by option("-drill-k", "--drillApiKey", envvar = "INPUT_DRILL_API_KEY").required()
    private val drillGroupId by option("-g", "--drillGroupId", envvar = "INPUT_GROUP_ID").required()
    private val drillAgentId by option("-a", "--drillAgentId", envvar = "INPUT_AGENT_ID").required()
    private val githubApiUrl by option("-gh-u", "--githubApiUrl", envvar = "GITHUB_API_URL").default("https://api.github.com")
    private val githubToken by option("-gh-t", "--githubToken", envvar = "INPUT_GITHUB_TOKEN").required()
    private val eventFilePath by option("-ef", "--eventFilePath", envvar = "GITHUB_EVENT_PATH").required()

    override fun run() {
        echo("Posting Drill4J Pull Request Report to GitHub by GitHub Event...")
        val githubCiCdService = GithubCiCdService(
            GithubApiClientImpl(githubApiUrl, githubToken),
            DrillApiClientImpl(drillApiUrl, drillApiKey),
            TextReportGenerator()
        )
        runBlocking {
            githubCiCdService.postPullRequestReportByEvent(
                File(eventFilePath),
                drillGroupId,
                drillAgentId
            )
        }
        echo("Done.")
    }
}
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

import com.epam.drill.integration.common.DrillApiClient
import com.epam.drill.integration.gitlab.DrillGitlabIntegration
import com.epam.drill.integration.gitlab.GitlabApiClient
import org.gradle.api.Plugin
import org.gradle.api.Project
import kotlinx.coroutines.runBlocking

class DrillIntegrationGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val gitlab = project.extensions.create("drillGitlab", DrillGitlabIntegration::class.java)

        project.task("drillGitlabReport") {
            doFirst {
                runBlocking {
                    val response = DrillApiClient.getMetricsSummary(gitlab)
                    GitlabApiClient.postComment(gitlab, response)
                }
            }
        }
    }
}



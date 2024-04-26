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
package com.epam.drill.integration.gitlab

import com.epam.drill.integration.common.DrillCIIntegration
import kotlinx.serialization.json.JsonObject
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*

open class DrillGitlabIntegration(
    var gitlabUrl: String? = null,
    var gitlabPrivateToken: String? = null,
): DrillCIIntegration()

object GitlabApiClient {
    private val client = HttpClient(CIO) {
        install(JsonFeature)
    }

    suspend fun postComment(payload: DrillGitlabIntegration, metrics: JsonObject) {
        val coverage = metrics["coverage"]
        val risks = metrics["risks"]
        val url = "${payload.gitlabUrl}/api/v4/projects/${payload.projectId}/merge_requests/${payload.mergeRequestId}/notes"

        client.post<JsonObject>(url) {
            contentType(ContentType.Application.Json)
            payload.gitlabPrivateToken?.let { token ->
                headers {
                    append("Private-Token", token)
                }
            }
            body = mapOf(
                "body" to """
                Drill4J Results:
                  Coverage: $coverage
                  Risks: $risks
            """.trimIndent()
            )
        }
    }
}
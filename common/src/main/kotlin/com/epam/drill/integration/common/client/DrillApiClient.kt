package com.epam.drill.integration.common.client

import kotlinx.serialization.json.JsonObject

interface DrillApiClient {
    suspend fun getDiffMetricsByCommits(
        groupId: String,
        agentId: String,
        sourceCommitSha: String,
        targetCommitSha: String
    ): JsonObject

    suspend fun getDiffMetricsByBranches(
        groupId: String,
        agentId: String,
        sourceBranch: String,
        targetBranch: String
    ): JsonObject
}
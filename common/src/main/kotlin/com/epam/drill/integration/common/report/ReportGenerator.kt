package com.epam.drill.integration.common.report

import kotlinx.serialization.json.JsonObject

interface ReportGenerator {
    fun getDiffSummaryReport(metrics: JsonObject): String
}
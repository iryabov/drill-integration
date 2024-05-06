package com.epam.drill.integration.common.report.impl

import com.epam.drill.integration.common.report.ReportGenerator
import kotlinx.serialization.json.JsonObject

class TextReportGenerator : ReportGenerator {
    override fun getDiffSummaryReport(metrics: JsonObject) =
        """
            Drill4J CI/CD report:
            - Coverage: ${metrics["coverage"]}%
            - Risks: ${metrics["risks"]}
        """.trimIndent()

}
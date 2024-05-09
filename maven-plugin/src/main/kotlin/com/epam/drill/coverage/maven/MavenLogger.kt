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
package com.epam.drill.coverage.maven

import com.epam.drill.coverage.PluginLogger
import org.slf4j.LoggerFactory

class MavenLogger : PluginLogger {
    private val logger = LoggerFactory.getLogger(javaClass)
    override fun error(msg: String) {
        logger.error(msg)
    }

    override fun warn(msg: String) {
        logger.warn(msg)
    }

    override fun info(msg: String) {
        logger.info(msg)
    }

    override fun debug(msg: String) {
        logger.debug(msg)
    }
}
package com.epam.drill.integration.github.model

import kotlinx.serialization.Serializable


@Serializable
data class GithubUser(val login: String)

@Serializable
data class GithubRepository(
    val name: String,
    val fullName: String,
    val owner: GithubUser
)

@Serializable
data class GithubPullRequestBranch(
    val sha: String,
    val ref: String
)

@Serializable
data class GithubPullRequest(
    val number: Int,
    val user: GithubUser,
    val head: GithubPullRequestBranch,
    val base: GithubPullRequestBranch
)

@Serializable
data class GithubEvent(
    val pullRequest: GithubPullRequest,
    val repository: GithubRepository
)
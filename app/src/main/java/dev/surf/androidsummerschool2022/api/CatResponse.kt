package dev.surf.androidsummerschool2022.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatResponse(
    @SerialName("id") val id: String?,
    @SerialName("created_at")  val createdAt: String?,
    @SerialName("tags") val tags: List<String>,
    @SerialName("url") val url: String?
)
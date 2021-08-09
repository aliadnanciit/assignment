package com.assignment.model


import com.squareup.moshi.Json

data class ShortURLResponse(
    @Json(name = "result")
    val result: Result,
    @Json(name = "ok")
    val ok: Boolean = false,
)


data class Result(
    @Json(name = "code")
    val code: String = "",
    @Json(name = "short_link")
    val shortLink: String = "",
    @Json(name = "short_link2")
    val shortLink2: String = "",
    @Json(name = "full_short_link2")
    val fullShortLink2: String = "",
    @Json(name = "full_short_link")
    val fullShortLink: String = "",
    @Json(name = "share_link")
    val shareLink: String = "",
    @Json(name = "full_share_link")
    val fullShareLink: String = "",
    @Json(name = "original_link")
    val originalLink: String = "",
)



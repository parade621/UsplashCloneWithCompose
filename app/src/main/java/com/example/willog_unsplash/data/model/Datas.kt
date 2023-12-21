package com.example.willog_unsplash.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "photos")
data class PhotoData(
    @field:Json(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @field:Json(name = "created_at")
    val created_at: String,
    @field:Json(name = "updated_at")
    val updated_at: String,
    @field:Json(name = "width")
    val width: Int,
    @field:Json(name = "height")
    val height: Int,
    @field:Json(name = "color")
    val color: String?,
    @field:Json(name = "blur_hash")
    val blur_hash: String?,
    @field:Json(name = "likes")
    val likes: Int,
    @field:Json(name = "user")
    val user: User,
    @field:Json(name = "urls")
    val urls: Urls,
    var isBookmarked: Boolean = false
)

@JsonClass(generateAdapter = true)
data class Urls(
    val raw: String
)

@JsonClass(generateAdapter = true)
data class User(
    val username: String
)

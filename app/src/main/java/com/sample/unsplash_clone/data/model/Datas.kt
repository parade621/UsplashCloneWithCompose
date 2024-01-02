package com.sample.unsplash_clone.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "photos", indices = [Index(value = ["id"], unique = true)])
data class PhotoData(
    @field:Json(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    @field:Json(name = "created_at")
    val created_at: String = "",
    @field:Json(name = "updated_at")
    val updated_at: String = "",
    @field:Json(name = "width")
    val width: Int = 0,
    @field:Json(name = "height")
    val height: Int = 0,
    @field:Json(name = "color")
    val color: String = "",
    @field:Json(name = "blur_hash")
    val blur_hash: String = "",
    @field:Json(name = "likes")
    val likes: Int = 0,
    @field:Json(name = "user")
    val user: User = User(),
    @field:Json(name = "urls")
    val urls: Urls = Urls(),
    var isBookmarked: Boolean = false
)

@JsonClass(generateAdapter = true)
data class Urls(
    val raw: String = "",
    val small: String = "",
    val thumb: String = ""
)

@JsonClass(generateAdapter = true)
data class User(
    val username: String = ""
)

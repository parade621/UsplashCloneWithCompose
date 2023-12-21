package com.example.willog_unsplash.data.db

import androidx.room.TypeConverter
import com.example.willog_unsplash.data.model.Urls
import com.example.willog_unsplash.data.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class OrmConverter {
    @TypeConverter
    fun fromList(value: List<String>): String {
        return Json.encodeToString(ListSerializer(String.serializer()), value)
    }

    @TypeConverter
    fun toList(value: String): List<String> {
        return Json.decodeFromString(ListSerializer(String.serializer()), value)
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun fromUser(user: User): String {
        val adapter = moshi.adapter(User::class.java)
        return adapter.toJson(user)
    }

    @TypeConverter
    fun toUser(userJson: String): User {
        val adapter = moshi.adapter(User::class.java)
        return adapter.fromJson(userJson)!!
    }

    @TypeConverter
    fun fromUrls(urls: Urls): String {
        val adapter = moshi.adapter(Urls::class.java)
        return adapter.toJson(urls)
    }

    @TypeConverter
    fun toUrls(urlsJson: String): Urls {
        val adapter = moshi.adapter(Urls::class.java)
        return adapter.fromJson(urlsJson)!!
    }
}
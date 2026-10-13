package com.lessonmaker.app.models.book

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksResponse(
    @SerialName("status")
    val status: String,

    @SerialName("message")
    val message: String,

    @SerialName("data")
    val data: List<Book>
)

@Serializable
data class Book(
    @SerialName("BookID")
    val bookID: Int,

    @SerialName("Title")
    val title: String,

    @SerialName("Author")
    val author: String,

    @SerialName("Category")
    val category: String,

    @SerialName("Price")
    val price: Double,

    @SerialName("Publisher")
    val publisher: String,

    @SerialName("PublishedYear")
    val publishedYear: Int,

    @SerialName("Language")
    val language: String,

    @SerialName("Country")
    val country: String
)
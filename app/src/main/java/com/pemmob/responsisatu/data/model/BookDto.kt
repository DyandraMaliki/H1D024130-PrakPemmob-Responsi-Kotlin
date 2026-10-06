package com.pemmob.responsisatu.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("numFound") val numFound: Int?,
    @SerializedName("docs") val docs: List<BookDto>?
)

data class BookDto(
    @SerializedName("key") val key: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("author_name") val authorName: List<String>?,
    @SerializedName("first_publish_year") val firstPublishYear: Int?,
    @SerializedName("edition_count") val editionCount: Int?,
    @SerializedName("language") val language: List<String>?
)

fun BookDto.toBook(): Book {
    return Book(
        title = title ?: "Tanpa Judul",
        author = authorName?.joinToString(", ")?.ifEmpty { "Penulis Tidak Diketahui" } ?: "Penulis Tidak Diketahui",
        firstPublishYear = firstPublishYear?.toString() ?: "Tahun Tidak Diketahui",
        editionCount = editionCount?.toString() ?: "0",
        language = language?.joinToString(", ")?.ifEmpty { "Bahasa Tidak Diketahui" } ?: "Bahasa Tidak Diketahui"
    )
}
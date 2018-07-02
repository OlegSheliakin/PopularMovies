package home.oleg.popularmovies.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReviewResponse(
        @SerializedName("id")
        @Expose
        val id: Int? = null,
        @SerializedName("page")
        @Expose
        val page: Int? = null,
        @SerializedName("results")
        @Expose
        val results: List<Result>? = null,
        @SerializedName("total_pages")
        @Expose
        val totalPages: Int? = null,
        @SerializedName("total_results")
        @Expose
        val totalResults: Int? = null) {

    data class Result(
            @SerializedName("id")
            @Expose
            val id: String? = null,
            @SerializedName("author")
            @Expose
            val author: String? = null,
            @SerializedName("content")
            @Expose
            val content: String? = null,
            @SerializedName("url")
            @Expose
            val url: String? = null)
}

package com.example.gowerexplorerapp3.model

class ReviewModel {
    val title: String
    val stars: Int
    val content: String
    val userId: String
    val poiId: String

    constructor(
        title: String,
        stars: Int,
        content: String,
        userId: String,
        poiId: String
    ) {
        this.title = title
        this.stars = stars
        this.content = content
        this.userId = userId
        this.poiId = poiId
    }

    override fun hashCode(): Int {
        return title.hashCode() + userId.hashCode() + content.hashCode()
    }

}
package com.example.gowerexplorerapp3.model

class ReviewModel {
    val titile: String
    val userId: String
    val content: String
    val poiId: String
    val stars: Int

    constructor(titile: String, userId: String, content: String, poiId: String, stars: Int) {
        this.titile = titile
        this.userId = userId
        this.content = content
        this.poiId = poiId
        this.stars = stars
    }


}
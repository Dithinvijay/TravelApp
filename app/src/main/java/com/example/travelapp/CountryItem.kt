package com.example.travelapp

class CountryItem {

    var imageResourse: Int = 0
    var title: String? = null
    var keyId: String? = null
    var favStatus: String? = null

    // Default constructor
    constructor()

    // Constructor with parameters
    constructor(imageResourse: Int, title: String?, keyId: String?, favStatus: String?) {
        this.imageResourse = imageResourse
        this.title = title
        this.keyId = keyId
        this.favStatus = favStatus
    }
}

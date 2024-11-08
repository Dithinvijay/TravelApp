package com.example.travelapp

class CountryItem {

    var imageResourse: Int = 0
    var title: String? = null
    var keyId: String? = null
    var favStatus: String? = null

    constructor(imageResourse: Int, title: String?, keyId: String?, favStatus: String?) {
        this.imageResourse = imageResourse
        this.title = title
        this.keyId = keyId
        this.favStatus = favStatus
    }
}

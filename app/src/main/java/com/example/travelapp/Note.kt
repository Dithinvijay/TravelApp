package com.example.travelapp

data class Note(
    var id: Long = 0,
    var title: String = "",
    var content: String = "",
    var date: String = "",
    var time: String = ""
) {
    constructor(title: String, content: String, date: String, time: String) : this(0, title, content, date, time)
}

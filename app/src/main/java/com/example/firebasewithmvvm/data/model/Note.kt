package com.example.firebasewithmvvm.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Note(
    var id: String = "",
    val text: String = "",
    @ServerTimestamp
    val date: Date = Date(),

    ) {
    constructor() : this("", "", date = Date())
}

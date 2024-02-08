package com.example.firebasewithmvvm.data.repository

import com.example.firebasewithmvvm.data.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class NoteRepositoryImp(
    val database: FirebaseFirestore
) : NoteRepository {

    override fun getNotes(): List<Note> {
        return arrayListOf(
            Note(id = "mansukh", text = "Note 1", date = Date()
            ),
            Note(id = "mansukh1", text = "Note 2", date = Date()
            ),
            Note(id = "mansukh2", text = "Note 3", date = Date()
            )
        )
    }
}
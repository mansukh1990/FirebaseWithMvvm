package com.example.firebasewithmvvm.repository

import com.example.firebasewithmvvm.data.model.Note
import com.example.firebasewithmvvm.util.FireStoreCollection
import com.example.firebasewithmvvm.util.UiState
import com.google.firebase.firestore.FirebaseFirestore

class NoteRepositoryImp(
    private val database: FirebaseFirestore
) : NoteRepository {

    override fun getNotes(result: (UiState<List<Note>>) -> Unit) {
        database.collection(FireStoreCollection.NOTE)
            .get()
            .addOnSuccessListener {
                val notes = arrayListOf<Note>()
                for (document in it) {
                    val note = document.toObject(Note::class.java)
                    notes.add(note)
                }
                result.invoke(
                    UiState.Success(notes)
                )

            }.addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )

            }
    }

    override fun addNote(note: Note, result: (UiState<Pair<Note, String>>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document()
        note.id = document.id
        document
            .set(note)
            .addOnSuccessListener {
                    result.invoke(
                    UiState.Success(Pair(note,"Note has been created successfully"))
                )
            }.addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage as String)
                )

            }
    }

    override fun updateNote(note: Note, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document(note.id)
        document
            .set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Note has been update successfully")
                )
            }.addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage as String)
                )

            }

    }

    override fun deleteNote(note: Note, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document(note.id)
        document
            .delete()
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Note has been deleted successfully")
                )
            }.addOnFailureListener { e ->
                result.invoke(
                    UiState.Failure(e.message)
                )

            }

    }
}
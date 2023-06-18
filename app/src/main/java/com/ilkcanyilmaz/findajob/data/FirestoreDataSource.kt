package com.ilkcanyilmaz.findajob.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface FirestoreDataSource {
    fun addDocumentById(
        documentId: String,
        document: Any,
        collectionName: String
    ): Task<Void>

    fun addDocument(document: Any, collectionName: String): Task<DocumentReference>
    fun setDocument(document: Any, collectionName: String): Task<DocumentReference>
    fun getDocument(documentId: String, collectionName: String): Task<DocumentSnapshot>
    fun updateDocument(documentId: String, document: Any, collectionName: String): Task<Void>
    fun deleteDocument(documentId: String, collectionName: String): Task<Void>
    fun getAllDocuments(collectionName: String): Task<QuerySnapshot>
    fun getAllDocumentsQuery(collectionName: String, field:String, value:Any): Task<QuerySnapshot>
}
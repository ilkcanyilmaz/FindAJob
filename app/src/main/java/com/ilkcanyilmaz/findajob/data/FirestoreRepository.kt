package com.ilkcanyilmaz.findajob.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class FirestoreRepository @Inject constructor(private val firestoreDataSource: FirestoreDataSource) {


    fun addDocumentById(
        documentId: String,
        document: Any,
        collectionName: String
    ): Task<Void> {
        return firestoreDataSource.addDocumentById(documentId, document, collectionName)
    }

    fun addDocument(document: Any, collectionName: String): Task<DocumentReference> {
        return firestoreDataSource.addDocument(document, collectionName)
    }

    fun getDocument(documentId: String, collectionName: String): Task<DocumentSnapshot> {
        return firestoreDataSource.getDocument(documentId, collectionName)
    }

    fun updateDocument(documentId: String, document: Any, collectionName: String): Task<Void> {
        return firestoreDataSource.updateDocument(documentId, document, collectionName)
    }

    fun deleteDocument(documentId: String, collectionName: String): Task<Void> {
        return firestoreDataSource.deleteDocument(documentId, collectionName)
    }

    fun getAllDocuments(collectionName: String): Task<QuerySnapshot> {
        return firestoreDataSource.getAllDocuments(collectionName)
    }

    fun getAllDocumentsQuery(
        collectionName: String,
        field: String,
        value: Any
    ): Task<QuerySnapshot> {
        return firestoreDataSource.getAllDocumentsQuery(collectionName,field,value)
    }

}




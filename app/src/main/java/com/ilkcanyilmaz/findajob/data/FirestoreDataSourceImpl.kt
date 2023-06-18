package com.ilkcanyilmaz.findajob.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class FirestoreDataSourceImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    FirestoreDataSource {

    override fun addDocumentById(
        documentId: String,
        document: Any,
        collectionName: String
    ): Task<Void> {
        return firestore.collection(collectionName).document(documentId).set(document)
    }

    override fun addDocument(document: Any, collectionName: String): Task<DocumentReference> {
        return firestore.collection(collectionName)
            .add(document)
    }

    override fun setDocument(document: Any, collectionName: String): Task<DocumentReference> {
        return firestore.collection(collectionName)
            .add(document)
    }

    override fun getDocument(documentId: String, collectionName: String): Task<DocumentSnapshot> {
        return firestore.collection(collectionName)
            .document(documentId)
            .get()
    }

    override fun updateDocument(
        documentId: String,
        document: Any,
        collectionName: String
    ): Task<Void> {
        return firestore.collection(collectionName)
            .document(documentId)
            .set(document)
    }

    override fun deleteDocument(documentId: String, collectionName: String): Task<Void> {
        return firestore.collection(collectionName)
            .document(documentId)
            .delete()
    }

    override fun getAllDocuments(collectionName: String): Task<QuerySnapshot> {
        return firestore.collection(collectionName).get()
    }

    override fun getAllDocumentsQuery(collectionName: String, field:String, value:Any): Task<QuerySnapshot> {
        return firestore.collection(collectionName).whereEqualTo(field,value).get()
    }
}

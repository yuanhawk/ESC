package tech.sutd.indoortrackingpro.data.implementation

import androidx.annotation.WorkerThread
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import java.util.concurrent.Executor

fun deleteCollection(collection: CollectionReference, executor: Executor) {
    Tasks.call(executor) {
        val batchSize = 10
        var query = collection.orderBy(FieldPath.documentId()).limit(batchSize.toLong())
        var deleted = deleteQueryBatch(query)

        while (deleted.size >= batchSize) {
            val last = deleted[deleted.size - 1]
            query = collection.orderBy(FieldPath.documentId()).startAfter(last.id).limit(batchSize.toLong())

            deleted = deleteQueryBatch(query)
        }
    }
}

@WorkerThread
@Throws(Exception::class)
private fun deleteQueryBatch(query: Query): List<DocumentSnapshot> {
    val querySnapshot = Tasks.await(query.get())

    val batch = query.firestore.batch()
    for (snapshot in querySnapshot) {
        batch.delete(snapshot.reference)
    }
    Tasks.await(batch.commit())

    return querySnapshot.documents
}
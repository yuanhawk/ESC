package tech.sutd.indoortrackingpro.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.realm.Realm
import tech.sutd.indoortrackingpro.data.helper.FirestoreHelper
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import java.util.*
import javax.inject.Inject

class FirestoreDb @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val realm: Realm
) : FirestoreHelper{

    private val TAG = "FirestoreDb"

    override fun insertAp(accessPoint: Account_mAccessPoints) {
        val data = hashMapOf(
            "mac" to accessPoint.mac,
            "rssi" to accessPoint.rssi,
            "ssid" to accessPoint.ssid
        )

        fStore.collection("mAccessPoints")
            .document(accessPoint.id.toString())
            .set(data)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override fun insertMp(mappingPoint: Account_mMappingPoints) {
        val data = hashMapOf(
            "x" to mappingPoint.x,
            "y" to mappingPoint.y,
        )

        fStore.collection("mMappingPoints")
            .document(mappingPoint.id.toString())
            .set(data)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override fun insertApRecord(accessPoint: Account_mAccessPoints) {
        val data = hashMapOf(
            "mac" to accessPoint.mac,
            "rssi" to accessPoint.rssi,
            "ssid" to accessPoint.ssid
        )

        fStore.collection("mAccessPointsRecorded")
            .document(accessPoint.id.toString())
            .set(data)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

}
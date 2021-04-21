package tech.sutd.indoortrackingpro.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import io.realm.RealmList
import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.data.helper.DbHelper
import tech.sutd.indoortrackingpro.data.helper.FirestoreHelper
import tech.sutd.indoortrackingpro.model.*
import javax.inject.Inject

class FirestoreDb @Inject constructor(
    private val fStore: FirebaseFirestore,
    private val db: DbHelper
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

    override fun insertInaccuracy(inaccuracy: Account_Inaccuracy) {
        val data = hashMapOf(
            "x" to inaccuracy.x,
            "y" to inaccuracy.y,
            "inaccuracy" to inaccuracy.inaccuracy
        )

        fStore.collection("inaccuracyRecorded")
            .document(inaccuracy.id.toString())
            .set(data)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override fun pullAp() {
        fStore.collection("mAccessPoints")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (ap in task.result!!) {
                        val result = ap.toObject<AccessPoints>()

                        val realmAp = Account_mAccessPoints(ObjectId(ap.id), result.mac, result.rssi, result.ssid)
                        db.insertAp(realmAp)
                    }
                }
            }
    }

    override fun pullMp() {
        val realmApList = RealmList<Account_mMappingPoints_accessPointsSignalRecorded>()

        fStore.collection("mAccessPointsRecorded")
            .get()
            .addOnCompleteListener { apList ->
                if (apList.isSuccessful) {
                    for (ap in apList.result!!) {
                        val result = ap.toObject<AccessPoints>()
                        val realmAp = Account_mMappingPoints_accessPointsSignalRecorded(
                            ObjectId(ap.id),
                            result.mac,
                            result.rssi,
                            result.ssid
                        )
                        realmApList.add(realmAp)
                    }
                }
            }

        fStore.collection("mMappingPoints")
            .get()
            .addOnCompleteListener { mpList ->
                if (mpList.isSuccessful) {
                    for (mp in mpList.result!!) {
                        val result = mp.toObject<MappingPoints>()

                        val realmMp = Account_mMappingPoints(
                            ObjectId(mp.id),
                            realmApList,
                            result.x,
                            result.y
                        )
                        db.insertMp(realmMp)
                    }
                }
            }
    }

}
package tech.sutd.indoortrackingpro.data.implementation

import android.util.Log
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import io.realm.RealmList
import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.data.AppExecutors
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

    override fun deleteAp(id: ObjectId) {
        fStore.collection("mAccessPoints")
            .document(id.toString())
            .delete()
    }

    override fun clearAp() {
        AppExecutors.instance?.let { deleteCollection(fStore.collection("mAccessPoints"), it.getDiskIO()) }
    }

    override fun insertMp(mappingPoint: Account_mMappingPoints) {
        val data = hashMapOf(
            "x" to mappingPoint.x,
            "y" to mappingPoint.y,
            "z" to mappingPoint.z
        )

        fStore.collection("mMappingPoints")
            .document(mappingPoint.id.toString())
            .set(data)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override fun deleteMp(id: ObjectId) {
        AppExecutors.instance?.let { deleteCollection(
            fStore.collection("mAccessPointsRecorded")
            .document(id.toString())
            .collection("apRecorded"), it.getDiskIO()) }

        fStore.collection("mMappingPoints")
            .document(id.toString())
            .delete()
    }

    override fun clearMp() {
        AppExecutors.instance?.let { deleteCollection(fStore.collection("mMappingPoints"), it.getDiskIO()) }
    }

    override fun insertApRecord(id: ObjectId, accessPoint: Account_mMappingPoints_accessPointsSignalRecorded) {
        val data = hashMapOf(
            "mac" to accessPoint.mac,
            "rssi" to accessPoint.rssi,
            "ssid" to accessPoint.ssid
        )

        fStore.collection("mAccessPointsRecorded")
            .document(id.toString())
            .collection("apRecorded")
            .add(data)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override fun clearApRecord() {
        AppExecutors.instance?.let { deleteCollection(fStore.collection("mAccessPointsRecorded"), it.getDiskIO()) }
    }

    override fun insertInaccuracy(inaccuracy: Account_Inaccuracy) {
        val data = hashMapOf(
            "x" to inaccuracy.x,
            "y" to inaccuracy.y,
            "z" to inaccuracy.z,
            "inaccuracy" to inaccuracy.inaccuracy
        )

        fStore.collection("inaccuracyRecorded")
            .document(inaccuracy.id.toString())
            .set(data)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override fun deleteInaccuracy(id: ObjectId) {
        fStore.collection("inaccuracyRecorded")
            .document(id.toString())
            .delete()
    }

    override fun clearInaccuracy() {
        AppExecutors.instance?.let { deleteCollection(fStore.collection("inaccuracyRecorded"), it.getDiskIO()) }
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

    override fun pullInaccuracy(){
        fStore.collection("inaccuracyRecorded").get().addOnCompleteListener{task->
            if (task.isSuccessful){
                for (inaccracyRecord in task.result!!){
                    val result = inaccracyRecord.toObject<Inaccuracy>()
                    val realmInaccuracy = Account_Inaccuracy(ObjectId(inaccracyRecord.id), result.x, result.y, result.z, result.inaccuracy)
                    db.insertInAccuracy(realmInaccuracy)
                }
            }

        }
    }

    override fun pullMp() {
        val realmApList = RealmList<Account_mMappingPoints_accessPointsSignalRecorded>()

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
                            result.y,
                            result.z
                        )

                        fStore.collection("mAccessPointsRecorded")
                            .document(mp.id)
                            .collection("apRecorded")
                            .get()
                            .addOnCompleteListener { apList ->
                                if (apList.isSuccessful) {
                                    for (ap in apList.result!!) {
                                        val recAp = ap.toObject<AccessPoints>()

                                        val realmAp = Account_mMappingPoints_accessPointsSignalRecorded(
                                            ObjectId(mp.id),
                                            recAp.mac,
                                            recAp.rssi,
                                            recAp.ssid
                                        )
                                        Log.d(TAG, "pullMp: $recAp")

                                        realmApList.add(realmAp)
                                    }
                                }
                            }

                        db.insertMp(realmMp)
                    }
                }
            }
    }

}
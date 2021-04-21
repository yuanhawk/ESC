package tech.sutd.indoortrackingpro.data.helper

import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.model.Account_Inaccuracy
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints_accessPointsSignalRecorded

interface FirestoreHelper {

    fun insertAp(accessPoint: Account_mAccessPoints)
    fun insertMp(mappingPoint: Account_mMappingPoints)
    fun insertApRecord(id: ObjectId, accessPoint: Account_mMappingPoints_accessPointsSignalRecorded)
    fun insertInaccuracy(inaccuracy: Account_Inaccuracy)

    fun deleteAp(id: ObjectId)
    fun deleteMp(id: ObjectId)
    fun deleteInaccuracy(id: ObjectId)

    fun clearAp()
    fun clearMp()
    fun clearApRecord()
    fun clearInaccuracy()

    fun pullAp()
    fun pullMp()
    fun pullInaccuracy()
}
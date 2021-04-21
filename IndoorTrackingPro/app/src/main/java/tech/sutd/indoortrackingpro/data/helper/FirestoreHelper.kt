package tech.sutd.indoortrackingpro.data.helper

import tech.sutd.indoortrackingpro.model.Account_Inaccuracy
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints

interface FirestoreHelper {

    fun insertAp(accessPoint: Account_mAccessPoints)
    fun insertMp(mappingPoint: Account_mMappingPoints)
    fun insertApRecord(accessPoint: Account_mAccessPoints)
    fun insertInaccuracy(inaccuracy: Account_Inaccuracy)

    fun pullAp()
    fun pullMp()
}
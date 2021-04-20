package tech.sutd.indoortrackingpro.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

/**
 * Each map has a corresponding account in the database (Right now we assume there is only a single account.
 * The account ID is stored as a constant inside utils.Constants. But in the future we may have multiple maps
 * and multiple accounts
 * @param mAccessPoints: the Wifi Aps used for mapping.
 * @param mMappingPoints: The mapping points recorded.
 */
open class Account(
    @PrimaryKey var _id: ObjectId = ObjectId(),
    var _partition: ObjectId? = null,
    var mAccessPoints: RealmList<Account_mAccessPoints> = RealmList(),
    var mMappingPoints: RealmList<Account_mMappingPoints> = RealmList(),
    var Inaccuracies: RealmList<Inaccuracy> = RealmList()
) : RealmObject() {

    constructor(
        mAccessPoints: RealmList<Account_mAccessPoints>
    ) : this() {
        this.mAccessPoints = mAccessPoints
    }
}
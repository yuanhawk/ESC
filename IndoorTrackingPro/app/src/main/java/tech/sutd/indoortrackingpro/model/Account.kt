package tech.sutd.indoortrackingpro.model



import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Each map has a corresponding account in the database (Right now we assume there is only a single account.
 * The account ID is stored as a constant inside utils.Constants. But in the future we may have multiple maps
 * and multiple accounts
 * @param mAccessPoints: the Wifi Aps used for mapping.
 * @param mMappingPoints: The mapping points recorded.
 */
open class Account(@PrimaryKey
                   var id: String = UUID.randomUUID().toString(),
                   var mAccessPoints: RealmList<AccessPoint> = RealmList(),
                   var mMappingPoints: RealmList<MappingPoint> = RealmList()): RealmObject(){
}
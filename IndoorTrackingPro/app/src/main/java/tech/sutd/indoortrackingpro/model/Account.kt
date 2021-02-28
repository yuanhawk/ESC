package tech.sutd.indoortrackingpro.model



import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

class Account(): RealmObject() {
    @PrimaryKey
    var id: String? = UUID.randomUUID().toString()
    var mAccessPoints: RealmList<AccessPoint> = RealmList()
    var mMappingPoints: RealmList<MappingPoint> = RealmList()
}
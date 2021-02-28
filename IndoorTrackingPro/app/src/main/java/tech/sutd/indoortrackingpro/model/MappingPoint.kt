package tech.sutd.indoortrackingpro.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


class MappingPoint(): RealmObject() {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var timeStamp: Date = Date()
    var coordinate: Coordinate = Coordinate()
    var accessPointSignalRecorded: RealmList<AccessPoint> = RealmList()

}
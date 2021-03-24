package tech.sutd.indoortrackingpro.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

// List of ap recorded (picked), realm db -> remote db

open class MappingPoint(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var timeStamp: Date = Date(),
    var longitude: Double = 0.0,
    var latitude: Double = 0.0,
    var accessPointSignalRecorded: RealmList<AccessPoint> = RealmList()): RealmObject() {

}
package tech.sutd.indoortrackingpro.model

import android.net.wifi.ScanResult
import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

// List of ap recorded (picked), realm db -> remote db

open class MappingPoint(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var x: Double = 0.0,
    var y: Double = 0.0,
    var accessPointSignalRecorded: RealmList<AccessPoint> = RealmList()): RealmObject(), Parcelable{
    constructor(parcel: Parcel) : this(){
        id = parcel.readString()!!
        x = parcel.readDouble()
        y = parcel.readDouble()
        parcel.readTypedList(accessPointSignalRecorded, AccessPoint.CREATOR)
    }
    constructor(scanResultList: List<ScanResult>): this(){
        for (scanResult in scanResultList){
            accessPointSignalRecorded.add(AccessPoint(scanResult))
        }
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeDouble(x)
        parcel.writeDouble(y)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MappingPoint> {
        override fun createFromParcel(parcel: Parcel): MappingPoint {
            return MappingPoint(parcel)
        }

        override fun newArray(size: Int): Array<MappingPoint?> {
            return arrayOfNulls(size)
        }
    }


}
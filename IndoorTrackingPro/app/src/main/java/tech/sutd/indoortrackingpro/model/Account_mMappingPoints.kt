package tech.sutd.indoortrackingpro.model

import android.net.wifi.ScanResult
import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

// List of ap recorded (picked), realm db -> remote db
open class Account_mMappingPoints(
    @PrimaryKey var _id: ObjectId = ObjectId(),
    var accessPointSignalRecorded: RealmList<APInsideMP> = RealmList(),
    var x: Double = 0.0,
    var y: Double = 0.0
) : RealmObject(), Parcelable{

    constructor(parcel: Parcel) : this(){
        _id = ObjectId()
        x = parcel.readDouble()
        y = parcel.readDouble()
        parcel.readTypedList(accessPointSignalRecorded, APInsideMP.CREATOR)
    }
    constructor(scanResultList: List<ScanResult>): this(){
        for (scanResult in scanResultList){
            accessPointSignalRecorded.add(APInsideMP(scanResult))
        }
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id.toString())
        parcel.writeDouble(x)
        parcel.writeDouble(y)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Account_mMappingPoints> {
        override fun createFromParcel(parcel: Parcel): Account_mMappingPoints {
            return Account_mMappingPoints(parcel)
        }

        override fun newArray(size: Int): Array<Account_mMappingPoints?> {
            return arrayOfNulls(size)
        }
    }

}
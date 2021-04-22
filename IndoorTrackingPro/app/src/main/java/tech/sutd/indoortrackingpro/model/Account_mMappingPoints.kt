package tech.sutd.indoortrackingpro.model

import android.net.wifi.ScanResult
import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId

// List of ap recorded (picked), realm db -> remote db
@RealmClass(embedded = true)
open class Account_mMappingPoints(
    var id: ObjectId = ObjectId(),
    var accessPointSignalRecorded: RealmList<Account_mMappingPoints_accessPointsSignalRecorded> = RealmList(),
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0
) : RealmObject(), Parcelable{

    constructor(parcel: Parcel) : this(){
        id = ObjectId()
        x = parcel.readDouble()
        y = parcel.readDouble()
        parcel.readTypedList(accessPointSignalRecorded, Account_mMappingPoints_accessPointsSignalRecorded.CREATOR)
    }
    constructor(scanResultList: List<ScanResult>): this(){
        for (scanResult in scanResultList){
            accessPointSignalRecorded.add(Account_mMappingPoints_accessPointsSignalRecorded(scanResult))
        }
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id.toString())
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
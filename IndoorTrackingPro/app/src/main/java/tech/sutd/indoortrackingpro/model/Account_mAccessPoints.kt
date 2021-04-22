package tech.sutd.indoortrackingpro.model

import android.net.wifi.ScanResult
import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.utils.noSignalDefaultRssi

// Signal strength, local db (realm), cache that is viewed as rv, pick some AP
@RealmClass(embedded = true)
open class Account_mAccessPoints(
    var id: ObjectId = ObjectId(),
    var mac: String = "",
    var rssi: Double = noSignalDefaultRssi,
    var ssid: String = ""
) : RealmObject(), Parcelable{

    constructor(other: Account_mAccessPoints): this(){
        this.id = ObjectId()  //this is very important!!
        this.mac = other.mac
        this.ssid = other.ssid
        this.rssi = noSignalDefaultRssi
    }

    constructor(scanResult: ScanResult): this(){
        mac = scanResult.BSSID
        ssid = scanResult.SSID
        rssi = scanResult.level.toDouble()
    }
    constructor(parcel: Parcel) : this() {
        id = ObjectId()
        mac = parcel.readString().toString()
        ssid = parcel.readString().toString()
        rssi = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id.toString())
        parcel.writeString(mac)
        parcel.writeString(ssid)
        parcel.writeDouble(rssi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Account_mAccessPoints> {
        override fun createFromParcel(parcel: Parcel): Account_mAccessPoints {
            return Account_mAccessPoints(parcel)
        }

        override fun newArray(size: Int): Array<Account_mAccessPoints?> {
            return arrayOfNulls(size)
        }
    }

}
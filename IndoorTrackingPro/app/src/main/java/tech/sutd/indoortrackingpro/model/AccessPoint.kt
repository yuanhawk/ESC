package tech.sutd.indoortrackingpro.model

import android.net.wifi.ScanResult
import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import tech.sutd.indoortrackingpro.utils.noSignalDefaultRssi
import java.util.*

// Signal strength, local db (realm), cache that is viewed as rv, pick some AP

open class AccessPoint(
    @PrimaryKey
    var id : String = UUID.randomUUID().toString(),
    //RealmObject requires null type, cannot be lateinit
    var mac: String = "",
    var ssid: String = "",
    var rssi: Double = noSignalDefaultRssi
):RealmObject(), Parcelable{

    constructor(other: AccessPoint): this(){
        this.id = UUID.randomUUID().toString()  //this is very important!!
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
        id = parcel.readString().toString()
        mac = parcel.readString().toString()
        ssid = parcel.readString().toString()
        rssi = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(mac)
        parcel.writeString(ssid)
        parcel.writeDouble(rssi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccessPoint> {
        override fun createFromParcel(parcel: Parcel): AccessPoint {
            return AccessPoint(parcel)
        }

        override fun newArray(size: Int): Array<AccessPoint?> {
            return arrayOfNulls(size)
        }
    }

}
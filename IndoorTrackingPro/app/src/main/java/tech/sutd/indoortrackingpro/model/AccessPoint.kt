package tech.sutd.indoortrackingpro.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


open class AccessPoint(): RealmObject(), Parcelable{
    @PrimaryKey
    var id : String = UUID.randomUUID().toString()
    //RealmObject requires null type, cannot be lateinit
    var mac: String = "";
    var ssid: String = "";
    var rssi: Double = 0.0;

    constructor(other: AccessPoint): this(){
        this.id = other.id;
        this.mac = other.mac;
        this.ssid = other.ssid;
        this.rssi = other.rssi;
    }


    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString();
        mac = parcel.readString().toString();
        ssid = parcel.readString().toString();
        rssi = parcel.readDouble();
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id);
        parcel.writeString(mac);
        parcel.writeString(ssid);
        parcel.writeDouble(rssi);
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
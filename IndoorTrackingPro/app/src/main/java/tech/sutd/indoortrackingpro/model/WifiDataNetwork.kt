package tech.sutd.indoortrackingpro.model

import android.net.wifi.ScanResult
import android.os.Parcel
import android.os.Parcelable

class WifiDataNetwork(): Comparable<WifiDataNetwork>, Parcelable {
    lateinit var mac: String
    lateinit var ssid: String
    var frequency: Int = 0
    var rssi: Int = 0
    constructor(scanResult: ScanResult): this(){
        mac = scanResult.BSSID
        ssid = scanResult.SSID
        frequency = scanResult.frequency
        rssi = scanResult.level
    }
    constructor(parcel: Parcel) : this() {
        mac = parcel.readString().toString()
        ssid = parcel.readString().toString()
        frequency = parcel.readInt()
        rssi = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mac)
        parcel.writeString(ssid)
        parcel.writeInt(frequency)
        parcel.writeInt(rssi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WifiDataNetwork> {
        override fun createFromParcel(parcel: Parcel): WifiDataNetwork {
            return WifiDataNetwork(parcel)
        }

        override fun newArray(size: Int): Array<WifiDataNetwork?> {
            return arrayOfNulls(size)
        }
    }

    override fun compareTo(other: WifiDataNetwork): Int {
        return other.rssi - this.rssi
    }
}
package tech.sutd.indoortrackingpro.data.helper

import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo

interface WifiHelper {

    fun startScan(): Boolean
    fun scanResults(): List<ScanResult>
    fun connectionInfo(): WifiInfo?
}
package tech.sutd.indoortrackingpro.data

import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import tech.sutd.indoortrackingpro.data.helper.WifiHelper
import java.lang.Exception
import javax.inject.Inject

class WifiWrapper @Inject constructor(
    private val wifiManager: WifiManager
) : WifiHelper {

    @SuppressWarnings("deprecation")
    override fun startScan(): Boolean =
        try {
            wifiManager.startScan()
        } catch (ex: Exception) {
            false
        }


    override fun scanResults(): List<ScanResult> =
        try {
            wifiManager.scanResults
        } catch (ex: Exception) {
            listOf()
        }


    override fun connectionInfo(): WifiInfo? =
        try {
            wifiManager.connectionInfo
        } catch (ex: Exception) {
            null
        }

}
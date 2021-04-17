package tech.sutd.indoortrackingpro.core

import android.util.Log
import io.realm.RealmList
import tech.sutd.indoortrackingpro.data.helper.AlgoHelper
import tech.sutd.indoortrackingpro.model.*
import tech.sutd.indoortrackingpro.utils.k
import tech.sutd.indoortrackingpro.utils.noSignalDefaultRssi
import kotlin.collections.ArrayList
import kotlin.math.pow

class TrackingAlgo : AlgoHelper {
    //return coordinate as String
    override fun predictCoordinate(
        wifiData: Account_mMappingPoints,
        account: Account,
        whichAlgo: String
    ): Coordinate? {
        val networkDataScanResult = wifiData.accessPointSignalRecorded
        val rssiValues: ArrayList<Double> = ArrayList()
        for (accessPoint: Account_mAccessPoints in account.mAccessPoints) {
            var flag = false
            for (j in 0 until networkDataScanResult.size) {
                if (accessPoint.mac == networkDataScanResult[j]!!.mac) {
                    rssiValues.add(networkDataScanResult[j]!!.rssi)

                    flag = true
                    break
                }
            }
            if (!flag) rssiValues.add(noSignalDefaultRssi)
        }
        if (whichAlgo == "WKNN") return knnPredict(account.mMappingPoints, rssiValues)
        return null
    }

    // unweighted KNN
    private fun knnPredict(
        mappingPointsList: RealmList<Account_mMappingPoints>,
        rssiValues: List<Double>
    ): Coordinate {
        val distanceLists: ArrayList<Distance> = ArrayList()
        for (mappingPoint in mappingPointsList) {
            val distance: Distance = calculateDistance(mappingPoint, rssiValues)
            distanceLists.add(distance)
            Log.d("Tracking Algo", "mapping point ${mappingPoint.x} ${mappingPoint.y}" + " spotted\n Distance: ${distance.distance}")
            Log.d("Tracking Algo", "${mappingPoint.accessPointSignalRecorded[0]!!.rssi}")
        }
        distanceLists.sort()
        var xSum = 0.0
        var ySum = 0.0
        var weightSum = 0.0
        for (i in 0 until if (distanceLists.size < k) distanceLists.size else k) {
            val temp_x = distanceLists[i].coordinate.longitude
            val temp_y = distanceLists[i].coordinate.latitude
            val weight = if (distanceLists[i].distance == 0.0) 100.0 else 1 / distanceLists[i].distance
            weightSum += weight
            xSum += temp_x * weight
            ySum += temp_y * weight
        }
        val x = xSum / weightSum
        val y = ySum / weightSum
        return Coordinate(x, y)
    }

    private fun calculateDistance(mappingPoint: Account_mMappingPoints, rssiValues: List<Double>): Distance {
        var temp = 0.0 //distance square
        for (i in rssiValues.indices) {
            temp += ((mappingPoint.accessPointSignalRecorded[i]?.rssi)!! - rssiValues[i]).pow(2.0)
        }
        return Distance(temp.pow(0.5), Coordinate(mappingPoint.x, mappingPoint.y))
        //for (i in 0..rssiValues.size)
    }
}
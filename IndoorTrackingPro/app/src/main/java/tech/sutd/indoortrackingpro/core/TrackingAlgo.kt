package tech.sutd.indoortrackingpro.core

import android.util.Log
import io.realm.RealmList
import tech.sutd.indoortrackingpro.model.*
import tech.sutd.indoortrackingpro.utils.Constants
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min
import kotlin.math.pow



//return coordinate as String
fun predictCoordinate(
    wifiData: MappingPoint,
    account: Account,
    whichAlgo: String
): Coordinate? {
    val networkDataScanResult = wifiData.accessPointSignalRecorded
    var rssiValues: ArrayList<Double> = ArrayList()
    for (accessPoint: AccessPoint in account.mAccessPoints) {
        var flag = false
        for (j in 0 until networkDataScanResult.size) {
            if (accessPoint.mac == networkDataScanResult[j]!!.mac) {
                rssiValues.add(networkDataScanResult[j]!!.rssi)

                flag = true
                break
            }
        }
        if (!flag) rssiValues.add(Constants.getNoSignalDefaultRssi())
    }
    if (whichAlgo == "WKNN") return knnPredict(account.mMappingPoints, rssiValues, true)
    return null
}

//TODO: unweighted KNN
private fun knnPredict(
    mappingPointsList: RealmList<MappingPoint>,
    rssiValues: List<Double>,
    isWeighted: Boolean
): Coordinate {
    val distanceLists: ArrayList<Distance> = ArrayList()
    //TODO
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
    for (i in 0 until if (distanceLists.size < Constants.getK()) distanceLists.size else Constants.getK()) {
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

private fun calculateDistance(mappingPoint: MappingPoint, rssiValues: List<Double>): Distance {
    var temp: Double = 0.0 //distance square
    for (i in rssiValues.indices) {
        temp += ((mappingPoint.accessPointSignalRecorded[i]?.rssi)!! - rssiValues[i]).pow(2.0)
    }
    return Distance(temp.pow(0.5), Coordinate(mappingPoint.x, mappingPoint.y))
    //TODO
    //for (i in 0..rssiValues.size)
}
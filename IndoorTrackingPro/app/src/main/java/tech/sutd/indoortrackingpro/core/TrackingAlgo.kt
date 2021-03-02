package tech.sutd.indoortrackingpro.core

import io.realm.RealmList
import tech.sutd.indoortrackingpro.model.*
import tech.sutd.indoortrackingpro.utils.Constants
import java.util.*
import kotlin.collections.ArrayList

class TrackingAlgo {
    companion object {
        fun getK() = 5 //How many nearest points to consider

        //return coordinate as String
        fun predictCoordinate(networkDataScanResult: List<WifiDataNetwork>, account: Account, whichAlgo: String): Coordinate?{
            var rssiValues: ArrayList<Double> = ArrayList()
            for (accessPoint: AccessPoint in account.mAccessPoints){
                var flag = false
                for (j in 0..networkDataScanResult.size){
                    if (accessPoint.mac == networkDataScanResult[j].mac) {
                        rssiValues.add(networkDataScanResult[j].rssi.toDouble())
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
        private fun knnPredict(mappingPointsList: RealmList<MappingPoint>, rssiValues: List<Double>, isWeighted: Boolean): Coordinate{
            val distanceLists: ArrayList<Distance> = ArrayList()
            //TODO
            for (mappingPoint in mappingPointsList){
                val distance: Distance = calculateDistance(mappingPoint, rssiValues)
                distanceLists.add(distance)
            }
            distanceLists.sort()
            var xSum = 0.0
            var ySum = 0.0
            var weightSum = 0.0
            for (i in 0.. Constants.getK()){
                val temp_x = distanceLists[i].coordinate.longitude
                val temp_y = distanceLists[i].coordinate.latitude
                val weight = if (distanceLists[i].distance == 0.0) 100.0 else 1/distanceLists[i].distance
                weightSum += weight
                xSum += temp_x * weight
                ySum += temp_y * weight
            }
            val x = xSum / weightSum
            val y = xSum / weightSum
            return Coordinate(x, y)
        }

        private fun calculateDistance(mappingPoint: MappingPoint, rssiValues: List<Double>): Distance{
            var temp: Double = 0.0 //distance square
            for (i in 0..rssiValues.size){
                temp += Math.pow((mappingPoint.accessPointSignalRecorded.get(i)?.rssi)!! - rssiValues[i], 2.0)
            }

            return Distance(Math.pow(temp, 0.5), Coordinate(mappingPoint.longitude, mappingPoint.latitude))
            //TODO
            //for (i in 0..rssiValues.size)
        }


    }
}
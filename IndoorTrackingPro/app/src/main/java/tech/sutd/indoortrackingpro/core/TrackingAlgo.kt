package tech.sutd.indoortrackingpro.core

import io.realm.RealmList
import tech.sutd.indoortrackingpro.model.*
import tech.sutd.indoortrackingpro.utils.Constants
class TrackingAlgo {
    companion object {
        fun getK() = 5 //How many nearest points to consider

        //return coordinate as String
        fun predictCoordinate(networkDataScanResult: List<WifiDataNetwork>, account: Account, whichAlgo: String): String?{
            var rssiValues: ArrayList<Double> = ArrayList()
            for (accessPoint: AccessPoint in account.mAccessPoints!!){
                var flag = false
                for (j in 0..networkDataScanResult.size){
                    if (accessPoint.mac == networkDataScanResult.get(j).mac) {
                        rssiValues.add(networkDataScanResult.get(j).rssi.toDouble())
                        flag = true
                        break
                    }
                }
                if (!flag) rssiValues.add(Constants.getNoSignalDefaultRssi())
            }
            if (whichAlgo == "WKNN") return knnPredict(account.mMappingPoints, rssiValues, true)
            return null
        }

        fun knnPredict(mappingPointsList: RealmList<MappingPoint>, rssiValues: List<Double>, isWeighted: Boolean): String?{
            var distanceLists: ArrayList<Distance> = ArrayList()
            //TODO
            return null
        }

        fun calculateDistance(mappingPoint: MappingPoint, rssiValues: List<Double>): Distance{
            var distance: Double = 0.0
            return Distance(0.0, 0.0, 0.0)
            //TODO
            //for (i in 0..rssiValues.size)
        }
    }
}
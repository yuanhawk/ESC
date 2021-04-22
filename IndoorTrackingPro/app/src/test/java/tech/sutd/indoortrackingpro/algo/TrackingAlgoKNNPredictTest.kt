package tech.sutd.indoortrackingpro.algo

import io.realm.RealmList
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tech.sutd.indoortrackingpro.core.TrackingAlgo
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints_accessPointsSignalRecorded
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import java.util.*

@RunWith(Parameterized::class)
class TrackingAlgoKNNPredictTest(var mappingPointListSignal: Array<Array<Double>>,
                                 var mappingPointListCoordinate: Array<Array<Double>>,
                                 var rssiValue: Array<Double>,
                                 var solution: Array<Double>) {

    val trackingAlgo = TrackingAlgo()
    companion object {
        @JvmStatic
        @Parameterized.Parameters fun parameters() =
            listOf(
                arrayOf(arrayOf(arrayOf(0.0, 0.0, 0.0), arrayOf(2.0, 2.0, 2.0), arrayOf(4.0, 4.0, 4.0)),
                        arrayOf(arrayOf(0.0, 0.0), arrayOf(1.0, 1.0), arrayOf(2.0, 2.0)),
                        arrayOf(1.0, 1.0, 1.0),
                        arrayOf(0.5, 0.5)),
                arrayOf(arrayOf(arrayOf(0.0, 0.0, 0.0), arrayOf(2.0, 2.0, 2.0), arrayOf(4.0, 4.0, 4.0)),
                        arrayOf(arrayOf(0.0, 0.0), arrayOf(3.0, 3.0), arrayOf(2.0, 2.0)),
                        arrayOf(1.0, 1.0, 1.0),
                        arrayOf(1.5, 1.5)),
                arrayOf(arrayOf(arrayOf(0.0, 0.0, 0.0), arrayOf(3.0, 3.0, 3.0), arrayOf(4.0, 4.0, 4.0)),
                    arrayOf(arrayOf(0.0, 0.0), arrayOf(3.0, 3.0), arrayOf(2.0, 2.0)),
                    arrayOf(1.0, 1.0, 1.0),
                    arrayOf(1.0, 1.0)),
                arrayOf(arrayOf(arrayOf(435.0, 231.0, 432.0), arrayOf(31.0, -123.0, 3.0), arrayOf(4.0, 4.0, 4.0)),
                    arrayOf(arrayOf(0.0, 0.0), arrayOf(132.0, 321.0), arrayOf(2.0, 2.0)),
                    arrayOf(1.0, 1.0, 1.0),
                    arrayOf(7.08700, 14.4827)),
            )
    }
    @Test
    fun calculateDistanceTest1(){
        val mpList = RealmList<Account_mMappingPoints>()
        for (i in mappingPointListSignal.indices){
            val mp = Account_mMappingPoints()
            for (apData in mappingPointListSignal[i]){
                val ap = Account_mMappingPoints_accessPointsSignalRecorded()
                ap.rssi = apData
                mp.accessPointSignalRecorded.add(ap)
            }
            mp.x = mappingPointListCoordinate[i][0]
            mp.y = mappingPointListCoordinate[i][1]
            mpList.add(mp)
        }
        val res = trackingAlgo.knnPredict(mpList, rssiValue.toList())
        assertEquals(res.longitude, solution[0], 0.001)
        assertEquals(res.latitude, solution[1], 0.001)
    }


}
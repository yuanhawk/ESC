package tech.sutd.indoortrackingpro.algo

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import tech.sutd.indoortrackingpro.core.TrackingAlgo
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints_accessPointsSignalRecorded
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import java.util.*

@RunWith(Parameterized::class)
class TrackingAlgoCalculateDistanceTest(var input1: Array<Double>, var input2: Array<Double>, var solution: Double) {

    val trackingAlgo = TrackingAlgo()
companion object {
    @JvmStatic
    @Parameterized.Parameters fun parameters() =
            listOf(
                    arrayOf(arrayOf<Double>(0.0, 0.0, 0.0), arrayOf<Double>(0.0, 0.0, 0.0), 0.0),
                    arrayOf(arrayOf<Double>(1.0, 1.0, 1.0), arrayOf<Double>(0.0, 0.0, 0.0), Math.pow(3.0, 1.0/2)),
                    arrayOf(arrayOf<Double>(1.0, 1.0, 1.0, 1.0), arrayOf<Double>(-1.0, -1.0, 0.0, -1.0), Math.pow(13.0, 1.0/2)),
                    arrayOf(arrayOf<Double>(2.0, 1.0, 1.0, 1.0), arrayOf<Double>(-1.0, -1.0, 0.0, -1.0), 4.24264),
                    arrayOf(arrayOf<Double>(1.0, 341.0, 235.0, 84.0), arrayOf<Double>(-16.0, -54.0, 123.0, -1.0), 419.62244),
                    arrayOf(arrayOf<Double>(1.0, 1.0, 1.0, 1.0), arrayOf<Double>(-1.0, -1.0, 0.0, -1.0), Math.pow(13.0, 1.0/2)),
            )
}
    @Test
    fun calculateDistanceTest1(){
        val mappingPoint = Account_mMappingPoints()
        for (element in input1) {
            val accessPoint1 = Account_mMappingPoints_accessPointsSignalRecorded()
            accessPoint1.rssi = element
            mappingPoint.accessPointSignalRecorded.add(accessPoint1)
        }
        val signal = input2.toList()
        val res = trackingAlgo.calculateDistance(mappingPoint, signal)
        assertEquals(solution, res.distance, 0.001)
    }


}
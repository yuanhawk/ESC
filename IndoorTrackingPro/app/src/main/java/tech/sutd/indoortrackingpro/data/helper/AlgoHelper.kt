package tech.sutd.indoortrackingpro.data.helper

import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Coordinate
import tech.sutd.indoortrackingpro.model.MappingPoint

interface AlgoHelper {
    fun predictCoordinate(
            wifiData: MappingPoint,
            account: Account,
            whichAlgo: String
    ): Coordinate?
}
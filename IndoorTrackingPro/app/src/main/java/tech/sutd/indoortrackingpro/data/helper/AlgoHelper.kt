package tech.sutd.indoortrackingpro.data.helper

import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Coordinate
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints

interface AlgoHelper {
    fun predictCoordinate(
        wifiData: Account_mMappingPoints,
        account: Account,
        whichAlgo: String
    ): Coordinate?
}
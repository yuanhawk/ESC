package tech.sutd.indoortrackingpro.utils

class Constants {
    companion object{
        fun getNoSignalDefaultRssi() = -110.0 //default value for an AP that is not detectable
        fun getK() = 5 //how many nearest point to consider in KNN algo
    }
}
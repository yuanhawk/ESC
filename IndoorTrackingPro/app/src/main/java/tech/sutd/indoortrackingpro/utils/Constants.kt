package tech.sutd.indoortrackingpro.utils

class Constants {
    companion object{
        fun getNoSignalDefaultRssi() = -110.0 //default value for an AP that is not detectable
        fun getK() = 2 //how many nearest point to consider in KNN algo
        fun getScanBatch() = 5
        fun getFetchInterval() = 200L //2 second
        fun getIntentFilter() = "ANDROID_WIFI_SCANNER"
        fun getIntentKey() = "WIFI_DATA"
    }
}
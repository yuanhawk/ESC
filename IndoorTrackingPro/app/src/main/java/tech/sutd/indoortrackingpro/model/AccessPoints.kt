package tech.sutd.indoortrackingpro.model

data class AccessPoints(
    var mac: String = "",
    var rssi: Double = 0.0,
    var ssid: String = ""
)
package tech.sutd.indoortrackingpro.model

import io.realm.RealmObject

class Coordinate: RealmObject() {
    var longitude: Double = 0.0
    var latitude: Double = 0.0
}
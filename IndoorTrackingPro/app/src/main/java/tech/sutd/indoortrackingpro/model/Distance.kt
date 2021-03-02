package tech.sutd.indoortrackingpro.model

class Distance(var distance: Double = 0.0,
        var mappingPointCoordinate: Coordinate): Comparable<Distance>{


    override fun compareTo(other: Distance): Int {
        return (this.distance - other.distance).toInt()
    }

}
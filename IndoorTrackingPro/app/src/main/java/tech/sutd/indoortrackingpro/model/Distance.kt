package tech.sutd.indoortrackingpro.model

class Distance(var distance: Double,
        var coordinate: Coordinate): Comparable<Distance>{


    override fun compareTo(other: Distance): Int {
        return (this.distance - other.distance).toInt()
    }

}
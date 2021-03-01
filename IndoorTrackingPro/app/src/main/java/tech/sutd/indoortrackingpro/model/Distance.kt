package tech.sutd.indoortrackingpro.model

class Distance(var distance: Double,
        var longitude: Double, var latitude: Double): Comparable<Distance>{


    override fun compareTo(other: Distance): Int {
        return (this.distance - other.distance).toInt()
    }

}
package tech.sutd.indoortrackingpro.ui


import java.util.*

class CustomTimerTask(map : CustomZoomImageView) : TimerTask(){
    val map = map




    override fun run() {

        val nextLoc : FloatArray = getLocation(map)
        nextLoc[0] = nextLoc[0]+50
        nextLoc[1] = nextLoc[1]-7.5f
        setLocation(map,nextLoc[0] , nextLoc[1])


    }

    fun setLocation( map : CustomZoomImageView , x : Float , y : Float){
        map.isEnabled = true
        map.pos[0] = x
        map.pos[1] = y
        map.invalidate()
}

    fun getLocation(map : CustomZoomImageView) : FloatArray {
        return map.pos
    }

}
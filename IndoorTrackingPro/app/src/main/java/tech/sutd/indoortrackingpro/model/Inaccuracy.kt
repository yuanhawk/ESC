package tech.sutd.indoortrackingpro.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class Inaccuracy(
    @PrimaryKey var _id: ObjectId? = ObjectId(),
    var x:Double = 0.0,
    var y:Double = 0.0,
    var inaccuracy: Double = 0.0
) : RealmObject() {

    constructor(
        x:Double, y: Double, inaccuracy: Double
    ) : this() {
        this.x = x
        this.y = y
        this.inaccuracy = inaccuracy
    }
}
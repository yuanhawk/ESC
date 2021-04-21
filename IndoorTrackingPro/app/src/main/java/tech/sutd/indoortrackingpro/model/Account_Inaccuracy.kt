package tech.sutd.indoortrackingpro.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId

@RealmClass(embedded = true)
open class Account_Inaccuracy(
    var id: ObjectId = ObjectId(),
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
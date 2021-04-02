package tech.sutd.indoortrackingpro.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class ListAP(
        _apList: RealmList<AP> = RealmList()
) : RealmObject() {
        @PrimaryKey var _id: ObjectId = ObjectId()
        var apList: RealmList<AP> = _apList
}
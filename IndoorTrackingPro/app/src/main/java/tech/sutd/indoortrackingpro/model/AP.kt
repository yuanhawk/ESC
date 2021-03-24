package tech.sutd.indoortrackingpro.model

import androidx.annotation.NonNull
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId

// will be shown as the recyclerview
@RealmClass(embedded = true)
open class AP(
        var mac: String = "",
        var ssid: String = "",
        var rssi: Int = 0
) : RealmObject()
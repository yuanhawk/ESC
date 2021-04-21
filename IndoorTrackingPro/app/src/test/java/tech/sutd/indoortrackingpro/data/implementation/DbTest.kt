package tech.sutd.indoortrackingpro.data.implementation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.TestLifecycle
import com.jraska.livedata.TestObserver
import io.realm.RealmList
import org.bson.types.ObjectId
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import tech.sutd.indoortrackingpro.data.helper.DbHelper
import tech.sutd.indoortrackingpro.model.Account_Inaccuracy
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints

class DbTest : DbHelper {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val accessPoints = mutableListOf<Account_mAccessPoints>()
    private val mappingPoints = mutableListOf<Account_mMappingPoints>()
    private val inaccuracies = mutableListOf<Account_Inaccuracy>()

    private val observableAccessPoints = MutableLiveData<List<Account_mAccessPoints>>()
    private val observableMappingPoints = MutableLiveData<List<Account_mMappingPoints>>()
    private val observableInaccuracy = MutableLiveData<List<Account_Inaccuracy>>()

    override fun insertAp(accessPoint: Account_mAccessPoints) {
        accessPoints.toMutableList().add(accessPoint)
        observableAccessPoints.postValue(accessPoints)
    }

    override fun insertMp(mappingPoint: Account_mMappingPoints) {
        mappingPoints.toMutableList().add(mappingPoint)
        observableMappingPoints.postValue(mappingPoints)
    }

    override fun insertInAccuracy(inaccuracy: Account_Inaccuracy) {
        inaccuracies.toMutableList().add(inaccuracy)
        observableInaccuracy.postValue(inaccuracies)
    }

    override fun clearAp() {
        accessPoints.clear()
        observableAccessPoints.postValue(accessPoints)
    }

    override fun clearMp() {
        mappingPoints.clear()
        observableMappingPoints.postValue(mappingPoints)
    }

    override fun clearInAccuracy() {
        inaccuracies.clear()
        observableInaccuracy.postValue(inaccuracies)
    }

    override fun deleteAp(id: ObjectId) {
        for (ap in accessPoints) {
            if (ap.id == id) {
                accessPoints.remove(ap)
            }
        }
        observableAccessPoints.postValue(accessPoints)
    }

    override fun deleteMp(id: ObjectId) {
        for (mp in mappingPoints) {
            if (mp.id == id) {
                mappingPoints.remove(mp)
            }
        }
        observableMappingPoints.postValue(mappingPoints)
    }

    override fun deleteInAccuracy(id: ObjectId) {
        for (acc in inaccuracies) {
            if (acc.id == id) {
                inaccuracies.remove(acc)
            }
        }
        observableInaccuracy.postValue(inaccuracies)
    }

    override fun retrieveApLiveData(): LiveData<RealmList<Account_mAccessPoints>>? {
        return observableAccessPoints as LiveData<RealmList<Account_mAccessPoints>>?
    }

    override fun retrieveMpLiveData(): LiveData<RealmList<Account_mMappingPoints>>? {
        return observableMappingPoints as LiveData<RealmList<Account_mMappingPoints>>?
    }

    override fun retrieveRecordInAccuracyLiveData(): LiveData<RealmList<Account_Inaccuracy>>? {
        return observableInaccuracy as LiveData<RealmList<Account_Inaccuracy>>?
    }

    private lateinit var testApObserver: TestObserver<List<Account_mAccessPoints>>
    private lateinit var testMpObserver: TestObserver<List<Account_mMappingPoints>>
    private lateinit var testInaccuracyObserver: TestObserver<List<Account_Inaccuracy>>
    private lateinit var testLifecycle: TestLifecycle
    private lateinit var apList: Account_mAccessPoints
    private lateinit var mpList: Account_mMappingPoints
    private lateinit var inaccuracy: Account_Inaccuracy

    @Before
    fun init() {
        testApObserver = TestObserver.create()
        testMpObserver = TestObserver.create()
        testInaccuracyObserver = TestObserver.create()
        testLifecycle = TestLifecycle.initialized()
        apList = Account_mAccessPoints(ObjectId(), "", 0.0, "")
        mpList = Account_mMappingPoints(ObjectId(), RealmList(), 0.0, 0.0)
        inaccuracy = Account_Inaccuracy(ObjectId(), 0.0, 0.0, 0.0)
    }

    @Test
    fun addAp() {
        retrieveApLiveData()?.observe(testLifecycle, testApObserver)

        insertAp(apList)

        testLifecycle.resume()

        testApObserver
            .assertHasValue()
            .assertHistorySize(1)
            .value()
    }


    @Test
    fun clearAllAp() {
        retrieveApLiveData()?.observe(testLifecycle, testApObserver)
        insertAp(apList)
        clearAp()

        testLifecycle.resume()

        testApObserver
            .assertHasValue()
            .assertHistorySize(1)
            .value()
    }

    @Test
    fun deleteSpecificAp() {
        retrieveApLiveData()?.observe(testLifecycle, testApObserver)
        insertAp(apList)
        deleteAp(apList.id)

        testLifecycle.resume()

        testApObserver
            .assertHasValue()
            .assertHistorySize(1)
            .value()
    }

    @Test
    fun addMp() {
        retrieveMpLiveData()?.observe(testLifecycle, testMpObserver)

        insertMp(mpList)

        testLifecycle.resume()

        testMpObserver
            .assertHasValue()
            .assertHistorySize(1)
            .value()
    }


    @Test
    fun clearAllMp() {
        retrieveMpLiveData()?.observe(testLifecycle, testMpObserver)
        insertMp(mpList)
        clearMp()

        testLifecycle.resume()

        testMpObserver
            .assertHasValue()
            .assertHistorySize(1)
            .value()
    }

    @Test
    fun deleteSpecificMp() {
        retrieveMpLiveData()?.observe(testLifecycle, testMpObserver)
        insertMp(mpList)
        deleteMp(mpList.id)

        testLifecycle.resume()

        testMpObserver
            .assertHasValue()
            .assertHistorySize(1)
            .value()
    }

    @Test
    fun addInaccuracy() {
        retrieveRecordInAccuracyLiveData()?.observe(testLifecycle, testInaccuracyObserver)

        insertInAccuracy(inaccuracy)

        testLifecycle.resume()

        testInaccuracyObserver
            .assertHasValue()
            .assertHistorySize(1)
            .value()
    }


    @Test
    fun clearAllInaccuracy() {
        retrieveRecordInAccuracyLiveData()?.observe(testLifecycle, testInaccuracyObserver)
        insertInAccuracy(inaccuracy)
        clearInAccuracy()

        testLifecycle.resume()

        testInaccuracyObserver
            .assertHasValue()
            .assertHistorySize(1)
            .value()
    }

    @Test
    fun deleteSpecificInaccurayc() {
        retrieveRecordInAccuracyLiveData()?.observe(testLifecycle, testInaccuracyObserver)
        insertInAccuracy(inaccuracy)
        deleteInAccuracy(mpList.id)

        testLifecycle.resume()

        testInaccuracyObserver
            .assertHasValue()
            .assertHistorySize(1)
            .value()
    }

    @After
    fun end() {
        retrieveApLiveData()?.removeObserver(testApObserver)
        retrieveMpLiveData()?.removeObserver(testMpObserver)
        retrieveRecordInAccuracyLiveData()?.removeObserver(testInaccuracyObserver)
    }


}
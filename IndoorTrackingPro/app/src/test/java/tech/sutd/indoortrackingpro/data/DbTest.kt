package tech.sutd.indoortrackingpro.data

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.log.RealmLog
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor
import org.powermock.modules.junit4.rule.PowerMockRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [24])
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest(Realm::class, RealmLog::class)
class DbTest {

    @get:Rule
    val rule = PowerMockRule()
    lateinit var testRealm: Realm

    @Before
    fun setup() {
        val testConfig = RealmConfiguration.Builder()
            .inMemory()
            .name("test-realm")
            .build()

        testRealm = Realm.getInstance(testConfig)

//        mockRealm = mockRealm()
    }

//    fun mockRealm(): Realm {
//        mockStatic(Realm::class.java)
//        val mockRealm = PowerMockito.mock(Realm::class.java)
//        `when`(mockRealm.createObject(Account_Inaccuracy::class.java)).thenReturn(Account_Inaccuracy())
//        `when`(mockRealm.createObject(Account_mAccessPoints::class.java)).thenReturn(
//            Account_mAccessPoints()
//        )
//        `when`(mockRealm.createObject(Account_mMappingPoints::class.java)).thenReturn(
//            Account_mMappingPoints()
//        )
//        `when`(mockRealm.createObject(Account_mMappingPoints_accessPointsSignalRecorded::class.java)).thenReturn(
//            Account_mMappingPoints_accessPointsSignalRecorded()
//        )
//        `when`(Realm.getDefaultInstance()).thenReturn(mockRealm)
//        return mockRealm
//    }

    @Test
    fun shouldBeAbleToGetDefaultInstance() {
        assertThat(Realm.getDefaultInstance(), `is`(testRealm))
    }

    @After
    fun end() {
        testRealm.close()
    }
}
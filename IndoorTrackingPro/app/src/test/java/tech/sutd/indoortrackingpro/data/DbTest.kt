package tech.sutd.indoortrackingpro.data

import io.realm.Realm
import io.realm.log.RealmLog
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.api.mockito.PowerMockito.mockStatic
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor
import org.powermock.modules.junit4.rule.PowerMockRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [19])
@PowerMockIgnore(*["org.mockito.*", "org.robolectric.*", "android.*"])
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest(*[Realm::class, RealmLog::class])
class DbTest {

    @get:Rule
    val rule = PowerMockRule()
    lateinit var mockRealm: Realm

    @Before
    fun setup() {
        mockStatic(Realm::class.java)
        mockStatic(RealmLog::class.java)

        val mockRealm = PowerMockito.mock(Realm::class.java)

        `when`(Realm.getDefaultInstance()).thenReturn(mockRealm)
        this.mockRealm = mockRealm
    }

    @Test
    fun shouldBeAbleToGetDefaultInstance() {
        assertThat(Realm.getDefaultInstance(), `is`(mockRealm))
    }
}
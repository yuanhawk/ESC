package tech.sutd.indoortrackingpro.di

import android.content.Context
import android.net.wifi.WifiManager
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    fun provideWifiManager(@ApplicationContext context: Context): WifiManager =
        context.getSystemService(Context.WIFI_SERVICE) as WifiManager
}
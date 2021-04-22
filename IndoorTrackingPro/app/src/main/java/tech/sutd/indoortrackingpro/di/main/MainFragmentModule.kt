package tech.sutd.indoortrackingpro.di.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import tech.sutd.indoortrackingpro.ui.adapter.ApListAdapter
import tech.sutd.indoortrackingpro.ui.adapter.MpListAdapter
import tech.sutd.indoortrackingpro.ui.adapter.WifiListAdapter

@InstallIn(FragmentComponent::class)
@Module
object MainFragmentModule {

    @FragmentScoped
    @Provides
    fun provideApListAdapter(): ApListAdapter = ApListAdapter()

    @FragmentScoped
    @Provides
    fun provideMpListAdapter(): MpListAdapter = MpListAdapter()

    @FragmentScoped
    @Provides
    fun provideWifiListAdapter(): WifiListAdapter = WifiListAdapter()
}
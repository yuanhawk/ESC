package tech.sutd.indoortrackingpro.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.sutd.indoortrackingpro.ui.adapter.ApListAdapter
import tech.sutd.indoortrackingpro.ui.wifi.ApListFragmentFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TestAppModule {

    @Singleton
    @Provides
    fun provideFragmentFactory(adapter : ApListAdapter): ApListFragmentFactory = ApListFragmentFactory(adapter)

    @Singleton
    @Provides
    fun provideApListAdapter(): ApListAdapter = ApListAdapter()




}

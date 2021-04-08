package tech.sutd.indoortrackingpro.di.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import tech.sutd.indoortrackingpro.ui.adapter.ApListAdapter

@InstallIn(FragmentComponent::class)
@Module
object MainModule {

    @FragmentScoped
    @Provides
    fun provideApListAdapter(): ApListAdapter = ApListAdapter()
}
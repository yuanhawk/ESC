package tech.sutd.indoortrackingpro.di.projectdetail

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.adapter.viewHolder.AccessPointSection
import tech.sutd.indoortrackingpro.adapter.viewHolder.MappingPointSection

@InstallIn(ActivityComponent::class)
@Module
object ProjectDetailModule {

    @ActivityScoped
    @Provides
    fun provideSectionedRecyclerViewAdapter(): SectionedRecyclerViewAdapter =
        SectionedRecyclerViewAdapter()

    @ActivityScoped
    @Provides
    fun provideSectionParameters(): SectionParameters =
        SectionParameters.builder()
            .headerResourceId(R.layout.item_point_header)
            .itemResourceId(R.layout.item_points)
            .build()

    @ActivityScoped
    @Provides
    fun provideAccessPointSection(
        sectionParameters: SectionParameters
    ): AccessPointSection = AccessPointSection(sectionParameters)

    @ActivityScoped
    @Provides
    fun provideMappingPointSection(
        sectionParameters: SectionParameters
    ): MappingPointSection = MappingPointSection(sectionParameters)
}
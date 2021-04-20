package tech.sutd.indoortrackingpro.di

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
object ActivityModule {

    @ActivityScoped
    @Provides
    fun provideHandler() = Handler(Looper.getMainLooper())

    @ActivityScoped
    @Provides
    fun provideBundle(): Bundle = Bundle()

    @ActivityScoped
    @Provides
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager =
        LinearLayoutManager(context)

    @ActivityScoped
    @Provides
    fun provideDividerItemDecoration(@ApplicationContext context: Context) =
        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

}
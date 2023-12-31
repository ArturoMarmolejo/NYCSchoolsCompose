package com.arturomarmolejo.nycschoolscompose.di

import com.arturomarmolejo.nycschoolscompose.data.rest.SchoolsRepository
import com.arturomarmolejo.nycschoolscompose.data.rest.SchoolsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideSchoolRepositoryImpl(schoolsRepositoryImpl: SchoolsRepositoryImpl):
            SchoolsRepository
}
package org.kmpcompose.kmp.di

import org.kmpcompose.kmp.dependencies.DbConnect
import org.kmpcompose.kmp.dependencies.MyRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::DbConnect)
}
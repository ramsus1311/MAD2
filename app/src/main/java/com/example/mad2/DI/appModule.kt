package com.example.mad2.di

import org.koin.dsl.module
import com.example.mad2.viewmodel.CitysearchViewModel

val appModule = module {
    single { CitysearchViewModel() }
}

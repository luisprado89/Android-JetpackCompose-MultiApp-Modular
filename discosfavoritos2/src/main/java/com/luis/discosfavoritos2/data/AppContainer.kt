package com.luis.discosfavoritos2.data

import android.content.Context

class AppContainer(context: Context) {
    private val appDatabase = AppDatabase.Companion.getDatabase(context)
    private val discoDao = appDatabase.discoDao()
    private val discoRepository = DiscoRepositoryImpl(discoDao)

    fun provideDiscoRepository(): DiscoRepository = discoRepository
}
package com.thoni.mvpprojetoismael

import android.app.Application
import com.thoni.mvpprojetoismael.core.di.AppContainer

class MvpProjetoIsmaelApp : Application() {
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}

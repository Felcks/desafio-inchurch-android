package matheusfelipe.desafio.inchurch.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@App)
            modules(listOf(DependencyModules.appModule))
        }
    }
}
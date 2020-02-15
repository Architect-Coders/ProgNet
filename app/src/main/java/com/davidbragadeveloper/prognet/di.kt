package com.davidbragadeveloper.prognet

import android.app.Application
import androidx.room.Room
import com.davidbragadeveloper.data.repository.AlbumsDataRepository
import com.davidbragadeveloper.data.source.AlbumLocalDataSource
import com.davidbragadeveloper.data.source.AlbumRemoteDataSource
import com.davidbragadeveloper.prognet.data.local.ProgNetDatabase
import com.davidbragadeveloper.prognet.data.local.RoomAlbumDataSource
import com.davidbragadeveloper.prognet.data.remote.DiscogsAlbumDataSource
import com.davidbragadeveloper.prognet.data.remote.DiscogsDb
import com.davidbragadeveloper.prognet.ui.main.MainActivity
import com.davidbragadeveloper.prognet.ui.main.MainViewModel
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository
import com.davidbragadeveloper.usecases.usecases.buildDiscoverProgAlbumsUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun Application.initDI(){
    startKoin{
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module{
    single(named("apiKey")) { BuildConfig.discogsApiKey }
    single(named("apiSecret")){ BuildConfig.discogsApiSecret }
    single(named("baseUrl")) { "https://api.themoviedb.org/3/" }
    single { DiscogsDb(get(named("baseUrl")))}
    single { ProgNetDatabase.build(androidContext())}
    factory<AlbumLocalDataSource> { RoomAlbumDataSource(get()) }
    factory<AlbumRemoteDataSource> { DiscogsAlbumDataSource(get()) }
}

private val dataModule = module {
    factory<AlbumsRepository> {
        AlbumsDataRepository(get(), get(), get(named("apiKey")),get(named("apiSecret")))
    }
}


private val scopesModule = module{
    scope(named<MainActivity>()) {
        scoped { buildDiscoverProgAlbumsUseCase(get()) }
        viewModel { MainViewModel(get()) }
    }
}

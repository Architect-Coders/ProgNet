package com.davidbragadeveloper.prognet

import android.app.Application
import com.davidbragadeveloper.data.repository.AlbumsDataRepository
import com.davidbragadeveloper.data.repository.LocationDataRepository
import com.davidbragadeveloper.data.repository.PermissionChecker
import com.davidbragadeveloper.data.source.AlbumLocalDataSource
import com.davidbragadeveloper.data.source.AlbumRemoteDataSource
import com.davidbragadeveloper.data.source.LocationDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.data.local.ProgNetDatabase
import com.davidbragadeveloper.prognet.data.local.RoomAlbumDataSource
import com.davidbragadeveloper.prognet.data.location.PlayServicesLocationDataSource
import com.davidbragadeveloper.prognet.data.permission.AndroidPermissionChecker
import com.davidbragadeveloper.prognet.data.remote.DiscogsAlbumDataSource
import com.davidbragadeveloper.prognet.data.remote.DiscogsDb
import com.davidbragadeveloper.prognet.ui.albumdetail.AlbumDetailActivity
import com.davidbragadeveloper.prognet.ui.albumdetail.AlbumDetailViewModel
import com.davidbragadeveloper.prognet.ui.main.MainActivity
import com.davidbragadeveloper.prognet.ui.main.MainViewModel
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository
import com.davidbragadeveloper.usecases.repositories.LocationRepository
import com.davidbragadeveloper.usecases.usecases.*
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
    factory<LocationDataSource>{ PlayServicesLocationDataSource(get())}
    factory<PermissionChecker>{ AndroidPermissionChecker(get())}
}

val dataModule = module {
    factory<AlbumsRepository> {
        AlbumsDataRepository(get(), get(), get(named("apiKey")),get(named("apiSecret")))
    }

    factory <LocationRepository>{
        LocationDataRepository(get(), get())
    }
}


private val scopesModule = module{
    scope(named<MainActivity>()) {
        scoped { buildDiscoverAlbumsUseCase(get()) }
        viewModel { MainViewModel(get()) }
    }

    scope(named<AlbumDetailActivity>()) {
        scoped(named("toggleAlbumHeared")){buildToggleAlbumHearedUseCase(get(), get())}
        scoped(named("getAlbumById")){buildGetAlbumByIdUseCase(get())}
        viewModel { (album: Album) ->
            AlbumDetailViewModel(
                shortAlbumData = album,
                getAlbumById = get(named("getAlbumById")) ,
                toggleAlbumHeared =  get(named("toggleAlbumHeared"))
            )
        }
    }
}

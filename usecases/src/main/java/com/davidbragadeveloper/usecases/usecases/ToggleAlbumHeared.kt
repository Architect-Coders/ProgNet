package com.davidbragadeveloper.usecases.usecases

import arrow.core.Try

typealias ToggleAlbumHeared = suspend (Long, Boolean) -> Try<Boolean>
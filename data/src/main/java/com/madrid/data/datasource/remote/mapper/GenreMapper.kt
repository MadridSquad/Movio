package com.madrid.data.datasource.remote.mapper

import com.madrid.data.datasource.remote.dto.genre.RemoteGenreDto
import com.madrid.domain.entity.Genre

fun RemoteGenreDto.toGenre() = Genre(
    id = this.id ?: 0,
    name = this.name ?: ""
)
package com.madrid.data.datasource.local.util

fun calculateOffset(page: Int) = (page - 1) * PAGE_SIZE
const val PAGE_SIZE = 20
package com.sobolev.beerratings.domain.model

import java.util.*

enum class Region {
    BELARUS,
    POLAND,
    UKRAINE,
    KAZAKHSTAN,
    GERMANY,
    CHINA,
    LATVIA;

    override fun toString() =
        this.name.lowercase(Locale.getDefault()).replaceFirstChar { it.uppercase() }
}
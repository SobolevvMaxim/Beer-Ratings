package com.sobolev.beerratings.domain.model

import androidx.annotation.DrawableRes

class RegionToDisplay(
    val region: Region,
    @DrawableRes val iconDrawable: Int,
    val available: Boolean
)
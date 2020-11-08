package com.aleksejantonov.core.controller

import com.aleksejantonov.core.controller.dto.BeerResponseDto
import retrofit2.http.GET

interface BeersController {

  @GET("/v2/beers")
  suspend fun getBeers(): List<BeerResponseDto>
}
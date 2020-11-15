package com.aleksejantonov.core.controller

import com.aleksejantonov.core.controller.dto.BeerResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersController {

  @GET("/v2/beers")
  suspend fun getBeers(
    @Query("page") page: Int,
    @Query("per_page") limit: Int
  ): List<BeerResponseDto>
}
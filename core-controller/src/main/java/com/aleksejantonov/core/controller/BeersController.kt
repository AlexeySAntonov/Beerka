package com.aleksejantonov.core.controller

import com.aleksejantonov.core.controller.dto.BeerResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersController {

  @GET("/v2/beers")
  suspend fun getBeers(
    @Query("page") page: Int,
    @Query("per_page") limit: Int,
    @Query("abv_gt") abvGreaterThan: Float,
    @Query("abv_lt") abvLessThan: Float,
    @Query("ibu_gt") ibuGreaterThan: Float,
    @Query("ibu_lt") ibuLessThan: Float,
    @Query("ebc_gt") ebcGreaterThan: Float,
    @Query("ebc_lt") ebcLessThan: Float,
  ): List<BeerResponseDto>
}
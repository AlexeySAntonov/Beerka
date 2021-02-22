package com.aleksejantonov.core.api

import com.aleksejantonov.core.controller.BeersController
import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.core.model.FilterModel

class BeersApi(private val controller: BeersController) {

  suspend fun getBeers(page: Int, limit: Int, filterRequest: FilterModel): List<BeerModel> {
    return controller.getBeers(
      page = page,
      limit = limit,
      abvGreaterThan = filterRequest.abvPair.first,
      abvLessThan = filterRequest.abvPair.second,
      ibuGreaterThan = filterRequest.ibuPair.first,
      ibuLessThan = filterRequest.ibuPair.second,
      ebcGreaterThan = filterRequest.ebcPair.first,
      ebcLessThan = filterRequest.ebcPair.second,
    ).map { it.model() }
  }
}
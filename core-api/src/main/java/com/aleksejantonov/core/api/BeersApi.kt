package com.aleksejantonov.core.api

import com.aleksejantonov.core.controller.BeersController
import com.aleksejantonov.core.model.BeerModel

class BeersApi(private val controller: BeersController) {

  suspend fun getBeers(page: Int, limit: Int): List<BeerModel> {
    return controller.getBeers(page = page, limit = limit).map { it.model() }
  }
}
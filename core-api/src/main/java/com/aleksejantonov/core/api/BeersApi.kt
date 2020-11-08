package com.aleksejantonov.core.api

import com.aleksejantonov.core.controller.BeersController
import com.aleksejantonov.core.model.BeerModel

class BeersApi(private val controller: BeersController) {

  suspend fun getBeers(): List<BeerModel> {
    return controller.getBeers().map { it.model() }
  }
}
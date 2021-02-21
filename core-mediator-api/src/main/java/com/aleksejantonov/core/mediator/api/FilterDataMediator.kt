package com.aleksejantonov.core.mediator.api

import com.aleksejantonov.core.model.FilterModel
import kotlinx.coroutines.flow.SharedFlow

interface FilterDataMediator {
  val filterDataFlow: SharedFlow<FilterModel>
}
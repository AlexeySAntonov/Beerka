package com.aleksejantonov.core.mediator.api

import com.aleksejantonov.core.model.FilterModel
import kotlinx.coroutines.flow.MutableSharedFlow

interface FilterDataMediator {
  val filterDataFlow: MutableSharedFlow<FilterModel>
}
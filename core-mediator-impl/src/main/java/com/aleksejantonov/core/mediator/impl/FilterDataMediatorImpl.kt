package com.aleksejantonov.core.mediator.impl

import com.aleksejantonov.core.mediator.api.FilterDataMediator
import com.aleksejantonov.core.model.FilterModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FilterDataMediatorImpl @Inject constructor() : FilterDataMediator {
  override val filterDataFlow = MutableSharedFlow<FilterModel>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
}
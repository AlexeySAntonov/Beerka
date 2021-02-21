package com.aleksejantonov.core.mediator.impl

import com.aleksejantonov.core.mediator.api.FilterDataMediator
import com.aleksejantonov.core.model.FilterModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class FilterDataMediatorImpl @Inject constructor() : FilterDataMediator {
  override val filterDataFlow: SharedFlow<FilterModel> = MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
}
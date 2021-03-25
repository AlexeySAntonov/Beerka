package com.aleksejantonov.module.injector

interface ScreenDataHolder<S : ScreenData> {
  var screenData: S
}
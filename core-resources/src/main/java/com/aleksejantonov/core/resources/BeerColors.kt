package com.aleksejantonov.core.resources

import com.aleksejantonov.core.resources.BeerColors.*

enum class BeerColors(val color: Int) {
  PALE_STRAW(-0x1d91),
  STRAW(-0x144e3),
  PALE_GOLD(-0x1263e7),
  DEEP_GOLD(-0x2982e2),
  PALE_AMBER(-0x399ede),
  MEDIUM_AMBER(-0x56badc),
  DEEP_AMBER(-0x73d0e6),
  AMBER_BROWN(-0x94e4f0),
  BROWN(-0xaaf7f8),
  RUBY_BROWN(-0xbefffe),
  DEEP_BROWN(-0xd0ffff),
  BLACK(-0xddffff),
}

fun beerColor(ebc: Float): Int {
  return when (ebc) {
    in 0.0..6.0 -> PALE_STRAW.color
    in 6.0..8.0 -> STRAW.color
    in 8.0..12.0 -> PALE_GOLD.color
    in 12.0..18.0 -> DEEP_GOLD.color
    in 18.0..24.0 -> PALE_AMBER.color
    in 24.0..30.0 -> MEDIUM_AMBER.color
    in 30.0..35.0 -> DEEP_AMBER.color
    in 35.0..39.0 -> AMBER_BROWN.color
    in 39.0..47.0 -> BROWN.color
    in 47.0..59.0 -> RUBY_BROWN.color
    in 59.0..79.0 -> DEEP_BROWN.color
    else -> BLACK.color
  }
}
package dev.mohammadfayaz.ccancel

/**
 * Note: Variables names used here violate clean code constraints, these are just for
 * demonstration purposes
 */
data class MainActivityState(
  val city: String,
  val pincode: String,
  val loading: Boolean,

  val city2: String,
  val pincode2: String,
  val loading2: Boolean
)

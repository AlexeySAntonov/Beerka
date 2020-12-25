package com.aleksejantonov.core.navigation.transition

import androidx.annotation.AnimRes
import com.aleksejantonov.core.navigation.R
import java.io.Serializable

sealed class ActivityTransition(
    @AnimRes open val openEnter: Int,
    @AnimRes open val openExit: Int,
    @AnimRes open val closeEnter: Int,
    @AnimRes open val closeExit: Int
) : Serializable {

  data class Slide(
    override val openEnter: Int = R.anim.activity_slide_in_from_right,
    override val openExit: Int = R.anim.activity_slide_out_to_left,
    override val closeEnter: Int = R.anim.activity_slide_in_from_left,
    override val closeExit: Int = R.anim.activity_slide_out_to_right,
  ) : ActivityTransition(openEnter, openExit, closeEnter, closeExit)

  data class Push(
      override val openEnter: Int = R.anim.activity_slide_in_from_bottom,
      override val openExit: Int = R.anim.activity_empty_exit,
      override val closeEnter: Int = R.anim.activity_empty_exit,
      override val closeExit: Int = R.anim.activity_slide_out_to_bottom,
  ) : ActivityTransition(openEnter, openExit, closeEnter, closeExit)
}

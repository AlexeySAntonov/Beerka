package com.aleksejantonov.beerka

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.aleksejantonov.beerka.ui.base.MainScreenLocalRouter
import com.aleksejantonov.beerka.ui.base.NonSwipeableViewPager
import com.aleksejantonov.core.navigation.BaseNavHostActivity
import com.aleksejantonov.core.navigation.navigation.PagerNavigation
import com.aleksejantonov.core.navigation.navigation.PersistentBottomSheetNavigation
import com.aleksejantonov.core.navigation.navigation.TabNavigation
import hellomobile.hello.core.ui.navigation.LocalRouter

class MainActivity : BaseNavHostActivity() {

  val localRouter by lazy {
//    val bottomSheetHost = supportFragmentManager
//      .findFragmentById(R.id.persistentBottomSheetContainer)
//      ?: throw  IllegalStateException("Bottom sheet host not found")
    MainScreenLocalRouter(
      activity = this,
      pagerNavigation = PagerNavigation(R.id.pager),
      tabNavigation = TabNavigation { //TODO move this navigation to MainTabsFragment
        supportFragmentManager.findFragmentByTag(makeFragmentName(0))
      },
      persistentBottomNavigation = PersistentBottomSheetNavigation(R.id.navContainer)
    )
  }

  private val pageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
    override fun onPageSelected(position: Int) {
      for (i in 0 until (findViewById<NonSwipeableViewPager>(R.id.pager).adapter?.count ?: 0)) {
        val f = supportFragmentManager.findFragmentByTag(makeFragmentName(i.toLong()))
//        (f as? OnShowFragmentListener)?.onShow(position == i)
      }
//      AndroidHelpers.hideKeyboard(binding.pager)
    }
  }

  override fun localRouter(): LocalRouter = localRouter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  companion object {
    private fun makeFragmentName(id: Long): String {
      return "android:switcher:" + R.id.pager + ":" + id
    }
  }
}
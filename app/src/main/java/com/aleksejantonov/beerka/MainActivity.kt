package com.aleksejantonov.beerka

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.aleksejantonov.beerka.ui.MainTabsFragment
import com.aleksejantonov.core.navigation.localrouting.MainScreenLocalRouter
import com.aleksejantonov.core.navigation.BaseNavHostActivity
import com.aleksejantonov.core.navigation.navigation.PagerNavigation
import com.aleksejantonov.core.navigation.navigation.PersistentBottomSheetNavigation
import com.aleksejantonov.core.navigation.navigation.TabNavigation
import com.aleksejantonov.core.navigation.localrouting.LocalRouter
import kotlinx.android.synthetic.main.activity_main.*

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



  override fun localRouter(): LocalRouter = localRouter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    pager.apply {
      adapter = PagerAdapter(supportFragmentManager)
      currentItem = 0
      addOnPageChangeListener(pageChangeListener)
    }
  }

  private val pageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
    override fun onPageSelected(position: Int) {
      for (i in 0 until (pager.adapter?.count ?: 0)) {
        val f = supportFragmentManager.findFragmentByTag(makeFragmentName(i.toLong()))
//        (f as? OnShowFragmentListener)?.onShow(position == i)
      }
//      AndroidHelpers.hideKeyboard(binding.pager)
    }
  }

  private class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
      return when (position) {
        0 -> MainTabsFragment()
        else -> throw IllegalStateException("no fragment for pos=$position")
      }
    }

    override fun getCount(): Int {
      return 1
    }
  }

  companion object {
    private fun makeFragmentName(id: Long): String {
      return "android:switcher:" + R.id.pager + ":" + id
    }
  }
}
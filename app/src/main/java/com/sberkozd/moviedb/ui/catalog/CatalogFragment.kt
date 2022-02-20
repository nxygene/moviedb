/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.sberkozd.moviedb.ui.catalog

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.sberkozd.moviedb.R
import com.sberkozd.moviedb.data.models.SortOptions
import com.sberkozd.moviedb.ui.home.HomeFragment

/**
 * Loads a grid of cards with videos to browse.
 */

class CatalogFragment : BrowseSupportFragment() {

    private lateinit var mBackgroundManager: BackgroundManager

    private var currentFragment: HomeFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBackgroundManager = BackgroundManager.getInstance(activity)
        activity?.let {
            mBackgroundManager.attach(it.window)
        }
        mainFragmentRegistry.registerFragment(
            PageRow::class.java,
            PageRowFragmentFactory(mBackgroundManager)
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupTitleAndHeaders()
        loadAndShowPlaylists()

        setBrowseTransitionListener(object : BrowseTransitionListener() {
            override fun onHeadersTransitionStart(withHeaders: Boolean) {
                if (withHeaders) {
                    currentFragment?.changeMarginOfRecyclerView(true)
                } else {
                    currentFragment?.changeMarginOfRecyclerView(false)
                }
                super.onHeadersTransitionStart(withHeaders)
            }
        })
        initializeBackground()
    }

    private fun initializeBackground() {
    }

    private fun loadAndShowPlaylists() {
    }


    private fun setupTitleAndHeaders() {
//        title = "BAŞLIK"

        // over title
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)

        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

        val headerItem1 = HeaderItem(0, "Popular")
        val pageRow1 = PageRow(headerItem1)
        rowsAdapter.add(pageRow1)
        val headerItem2 = HeaderItem(1, "Top Rated")
        val pageRow2 = PageRow(headerItem2)
        rowsAdapter.add(pageRow2)
        val headerItem3 = HeaderItem(2, "Most Revenue")
        val pageRow3 = PageRow(headerItem3)
        rowsAdapter.add(pageRow3)
        val headerItem4 = HeaderItem(3, "Release Date")
        val pageRow4 = PageRow(headerItem4)
        rowsAdapter.add(pageRow4)

        adapter = rowsAdapter
    }

    inner class PageRowFragmentFactory(private val mBackgroundManager: BackgroundManager) :
        BrowseSupportFragment.FragmentFactory<Fragment>() {
        override fun createFragment(rowObj: Any): Fragment {
            val row = rowObj as Row
            mBackgroundManager.drawable = null
            if (row.headerItem.id == 0L) {
                title = "Popular"
                currentFragment = HomeFragment().apply {
                    arguments = Bundle().apply { putString("sort", SortOptions.POPULAR.key) }
                }
            } else if (row.headerItem.id == 1L) {
                title = "Top Rated"
                currentFragment = HomeFragment().apply {
                    arguments = Bundle().apply { putString("sort", SortOptions.TOP_RATED.key) }
                }
            } else if (row.headerItem.id == 2L) {
                title = "Revenue"
                currentFragment = HomeFragment().apply {
                    arguments = Bundle().apply { putString("sort", SortOptions.REVENUE.key) }
                }
            } else if (row.headerItem.id == 3L) {
                title = "Release Date"
                currentFragment = HomeFragment().apply {
                    arguments = Bundle().apply { putString("sort", SortOptions.RELEASE_DATE.key) }
                }
            }
            currentFragment?.let { return it }
            throw IllegalArgumentException(String.format("Invalid row %s", rowObj))
        }
    }
}
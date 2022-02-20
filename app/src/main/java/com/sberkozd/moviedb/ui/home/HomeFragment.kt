package com.sberkozd.moviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import com.sberkozd.moviedb.R
import com.sberkozd.moviedb.databinding.FragmentHomeBinding
import com.sberkozd.moviedb.ui.adapters.MovieAdapter
import com.sberkozd.moviedb.util.observeInLifecycle
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home),
    BrowseSupportFragment.MainFragmentAdapterProvider, MovieAdapter.MovieSelectionListener {

    private val vm: HomeViewModel by viewModels()

    private val mMainFragmentAdapter: BrowseSupportFragment.MainFragmentAdapter<*> =
        BrowseSupportFragment.MainFragmentAdapter(this)

    private var testVar = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@HomeFragment
            viewModel = vm
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.eventsFlow.onEach {
            when (it) {
                HomeViewModel.Event.ShowNoDataWarning -> {
                    vm.toast = getString(R.string.error_no_more_data_found)
                }
            }
        }.observeInLifecycle(viewLifecycleOwner)


        binding.fragmentHomeRvPopular.setTextViewForTitle(binding.fragmentHomeTvMovieName)

        binding.fragmentHomeRvPopular.postDelayed({
            if ((binding.fragmentHomeRvPopular.adapter as MovieAdapter).listener == null) {
                (binding.fragmentHomeRvPopular.adapter as MovieAdapter).setMovieSelectionListener(
                    this
                )
            }
        }, 1000)

        if (testVar == 1903) {
            binding.fragmentHomeRvPopular.animate().translationX(0f).setDuration(0)
            binding.fragmentHomeTvMovieName.animate().translationX(0f).setDuration(0)
        }

    }

    override fun getMainFragmentAdapter(): BrowseSupportFragment.MainFragmentAdapter<*> {
        return mMainFragmentAdapter
    }

    fun changeMarginOfRecyclerView(moveLeft: Boolean) {

        val leftMargin: Int = if (moveLeft) {
            -300
        } else {
            0
        }
        binding.fragmentHomeRvPopular.animate().translationX(leftMargin.toFloat()).setDuration(500)
            .setInterpolator(AccelerateInterpolator())
        binding.fragmentHomeTvMovieName.animate().translationX(leftMargin.toFloat())
            .setDuration(500).setInterpolator(AccelerateInterpolator())

    }

    override fun onMovieSelected() {
        testVar = 1903
    }

}
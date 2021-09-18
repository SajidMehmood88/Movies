package com.zattoo.movies.presentation.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.zattoo.movies.R
import com.zattoo.movies.core.utils.NetworkUtils
import com.zattoo.movies.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by sharedViewModel<HomeViewModel>()
    private lateinit var adapter: HomeAdapter
    private val networkUtils: NetworkUtils by inject()

    companion object {
        const val ANIMATION_DURATION = 1000L
        const val EMPTY_LIST = 204
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUiElements()
        initObservers()
    }

    private fun initObservers() {
        with(homeViewModel) {
            viewState.observe(viewLifecycleOwner, { state ->
                run {
                    state?.let {
                        when (it) {
                            is UIState.Ready -> {
                                handleResults(it)
                            }

                            is UIState.Loading -> {
                                handleLoading()
                            }

                            is UIState.Error -> {
                                handleError(it)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun handleLoading() {
        showLoading(true)
    }

    private fun handleResults(it: UIState.Ready) {
        showLoading(false)
        adapter.submitList(it.list)
    }

    private fun handleError(it: UIState.Error) {
        showLoading(false)
        val errorMessage = getString(it.error)
        if (it.error == R.string.empty_list) {
            showEmptyList(errorMessage)
        } else {
            showError(errorMessage)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showEmptyList(message: String) {
        binding.recyclerView.visibility = View.GONE
        binding.emptyView.visibility = View.VISIBLE
        binding.emptyView.text = message
    }

    private fun initUiElements() {
        binding.run {
            adapter = HomeAdapter()
            binding.recyclerView.adapter = adapter

            swipeRefreshLayout.setOnRefreshListener { fetchData() }
        }
    }

    override fun onStart() {
        super.onStart()
        fetchData()
        handleNetwork()
    }

    private fun handleNetwork() {
        networkUtils.getNetworkLiveData().observe(this) { isConnected ->
            if (!isConnected) {
                binding.textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                binding.networkStatusLayout.apply {
                    visibility = View.VISIBLE
                    setBackgroundColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.colorStatusNotConnected,
                            null
                        )
                    )
                }
                binding.swipeRefreshLayout.isRefreshing = false
            } else {
                homeViewModel.fetchMovies()
                binding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                binding.networkStatusLayout.apply {
                    setBackgroundColor(
                        ResourcesCompat.getColor(
                            resources, R.color.colorStatusConnected, null
                        )
                    )
                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                visibility = View.GONE
                            }
                        })
                }
            }
        }
    }

    private fun fetchData() {
        homeViewModel.fetchMovies()
    }
}
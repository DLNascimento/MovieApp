package com.example.movieapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.FragmentGridMoviesBinding
import com.example.movieapp.ui.adapter.GridMovieAdapter
import com.example.movieapp.utils.ResourceState
import com.example.movieapp.utils.hide
import com.example.movieapp.utils.show
import com.example.movieapp.viewmodel.GridMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GridMovies : Fragment() {

    private lateinit var binding: FragmentGridMoviesBinding
    private val viewModel: GridMoviesViewModel by viewModels()
    private val gridMovieAdapter by lazy { GridMovieAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGridMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectObservers()
        setupRecyclerView()
        navigate()
    }

    private fun setupRecyclerView() = with(binding) {
        rvPopularMovie.apply {
            adapter = gridMovieAdapter
            layoutManager = GridLayoutManager(requireContext(),
                3, GridLayoutManager.VERTICAL,
                false)
        }
    }

    private fun collectObservers() {

        lifecycleScope.launch {
            viewModel.movieList.collect{
                gridMovieAdapter.submitData(it)
            }
        }

    }

    private fun navigate(){
        gridMovieAdapter.setOnItemClickListener {
            val action = GridMoviesDirections.actionGridMoviesToMovieDetails()
            findNavController().navigate(action)
        }
    }
}

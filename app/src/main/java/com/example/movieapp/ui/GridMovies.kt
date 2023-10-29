package com.example.movieapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movieapp.databinding.FragmentGridMoviesBinding
import com.example.movieapp.ui.adapter.GridMovieAdapter
import com.example.movieapp.utils.ResourceState
import com.example.movieapp.viewmodel.GridMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
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
    }

    private fun setupRecyclerView() = with(binding) {
        rvPopularMovie.apply {
            adapter = gridMovieAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun collectObservers() {
        viewModel.listaMoviesPopular.observe(requireActivity()){response ->
            when (response) {
                is ResourceState.Success -> {
                    response.data.let { newResponse ->
                        Log.e("SUCESSO", "CARREGOU!")
                        gridMovieAdapter.movies = newResponse!!.results.toList()
                    }
                }

                is ResourceState.Error -> {
                    response.message
                }

                is ResourceState.Load -> {
                    Toast.makeText(requireContext(), "CARREGANDO", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }
}

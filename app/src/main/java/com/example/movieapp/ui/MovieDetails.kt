package com.example.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.utils.Constants
import com.example.movieapp.viewmodel.GridMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetails : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private var movieId = 0
    private val args: MovieDetailsArgs by navArgs()
    private val viewModel: GridMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = args.movieId
        if (movieId > 0){
            viewModel.loadDetailMovie(movieId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.detailMovie.observe(viewLifecycleOwner){ response ->
                ImgDetailsMovie.load("${Constants.BASE_URL_IMAGEM}${response.posterPath}"){
                    crossfade(true)
                    crossfade(1000)
                }

                movieTitle.text = response.title
                movieTagline.text = response.tagline
                movieReleaseDate.text = response.releaseDate
                movieRating.text = response.voteAverage.toString()
                movieRuntime.text = response.runtime.toString()
                movieOverview.text = response.overview
            }

            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    prgBarMovies.visibility = View.VISIBLE
                } else {
                    prgBarMovies.visibility = View.INVISIBLE
                }
            }
        }
    }

}
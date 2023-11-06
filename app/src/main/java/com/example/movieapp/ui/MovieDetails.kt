package com.example.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.movieapp.R
import com.example.movieapp.data.local.MoviesEntity
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.utils.Constants
import com.example.movieapp.viewmodel.GridMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetails : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private var movieId = 0
    private val args: MovieDetailsArgs by navArgs()
    private val viewModel: GridMoviesViewModel by viewModels()

    @Inject
    lateinit var entity: MoviesEntity

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
                movieRuntime.text = response.runtime.toString() + " minutes"
                movieOverview.text = response.overview

                ImgFav.setOnClickListener {
                    entity.id = movieId
                    entity.poster_path = response.posterPath
                    entity.title = response.title
                    entity.release_date = response.releaseDate
                    viewModel.favoriteMovie(movieId, entity)
                }
            }

            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    prgBarMovies.visibility = View.VISIBLE
                } else {
                    prgBarMovies.visibility = View.INVISIBLE
                }
            }

            lifecycleScope.launch {
                if (viewModel.existMovie(movieId)) {
                    ImgFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                } else {
                    ImgFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.philippineSilver))
                }
            }

            viewModel.isFavorite.observe(viewLifecycleOwner) {
                if (it) {
                    ImgFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))

                } else {
                    ImgFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.philippineSilver))
                }
            }
        }
    }

}
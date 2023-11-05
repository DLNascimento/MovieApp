package com.example.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentFavoritesBinding
import com.example.movieapp.ui.adapter.FavoriteMoviesAdapter
import com.example.movieapp.utils.initRecycler
import com.example.movieapp.viewmodel.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    @Inject
    lateinit var favoriteMoviesAdapter: FavoriteMoviesAdapter

    private val viewModel: DatabaseViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.loadFavoriteMovieList()
            viewModel.favoriteMovieList.observe(viewLifecycleOwner){
                favoriteMoviesAdapter.bind(it)
                favoriteRecycler.initRecycler(GridLayoutManager(requireContext(), 3),favoriteMoviesAdapter)
            }

            favoriteMoviesAdapter.setonItemClickListener {
                val direction = FavoritesFragmentDirections.actionFavoritesFragmentToMovieDetails(it.id)
                findNavController().navigate(direction)
            }

          /*  viewModel.emptyList.observe(viewLifecycleOwner){
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    favoriteRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    favoriteRecycler.showInvisible(true)
                }
            }*/
        }
    }

}
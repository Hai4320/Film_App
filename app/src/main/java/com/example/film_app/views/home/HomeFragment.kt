package com.example.film_app.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.film_app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var vm: HomeVM
    lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this).get(HomeVM::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMovieList()
        registerMovieList()
        registerErrorList()
    }


    override fun onStart() {
        super.onStart()
        vm.getComingUpMovie()
    }

    private fun setUpMovieList() {
        adapter = MovieAdapter()
        val lm = LinearLayoutManager(context)
        binding.rv.layoutManager = lm
        binding.rv.adapter = adapter
    }

    private fun registerMovieList() {
        vm.movieData.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun registerErrorList() {
        vm.errEvent.observe(this){
            if (it !== null) {
                Toast.makeText(this.context, it!!, Toast.LENGTH_LONG).show()
            }
        }
    }


}
package com.example.firebasewithmvvm.note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.firebasewithmvvm.R
import com.example.firebasewithmvvm.data.util.UiState
import com.example.firebasewithmvvm.data.util.hide
import com.example.firebasewithmvvm.data.util.show
import com.example.firebasewithmvvm.data.util.toast
import com.example.firebasewithmvvm.data.viewmodel.NoteViewModel
import com.example.firebasewithmvvm.databinding.FragmentNoteListingragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListingFragment : Fragment() {

    val TAG: String = "NoteListingFragment"
    private lateinit var binding: FragmentNoteListingragmentBinding
    private val viewModel by viewModels<NoteViewModel>()
    val adapter by lazy {
        NoteListingAdapter(
            onDeleteClicked = { pos, item ->},
            onEditClicked = { pos , item -> },
            onItemClicked = { pos , item -> }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListingragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_noteListingFragment_to_noteDetailFragment)
        }
        viewModel.getNotes()
        viewModel.note.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                   binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapter.updateList(state.data.toMutableList())

                }
            }

        }

    }
}
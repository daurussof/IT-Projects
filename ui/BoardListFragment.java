package com.example.jira.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jira.MainViewModel;
import com.example.jira.databinding.FragmentBoardListBinding;
import com.example.jira.ui.adapters.BoardAdapter;

public class BoardListFragment extends Fragment {

    private FragmentBoardListBinding binding;
    private MainViewModel viewModel;
    private BoardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBoardListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        adapter = new BoardAdapter(board -> {
            Bundle args = new Bundle();
            args.putString("boardId", board.getId());
            args.putString("boardName", board.getName());
            Navigation.findNavController(binding.getRoot()).navigate(
                    com.example.jira.R.id.action_boardListFragment_to_issueListFragment,
                    args
            );
        });

        binding.boardRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.boardRecycler.setAdapter(adapter);

        viewModel.getBoards().observe(getViewLifecycleOwner(), boards -> adapter.submitList(boards));
    }
}


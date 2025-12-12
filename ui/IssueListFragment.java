package com.example.jira.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jira.MainViewModel;
import com.example.jira.R;
import com.example.jira.data.model.User;
import com.example.jira.databinding.FragmentIssueListBinding;
import com.example.jira.ui.adapters.IssueAdapter;

public class IssueListFragment extends Fragment {

    private FragmentIssueListBinding binding;
    private MainViewModel viewModel;
    private IssueAdapter adapter;
    private String boardId;
    private String boardName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIssueListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            boardId = args.getString("boardId");
            boardName = args.getString("boardName");
        }

        binding.boardTitle.setText(boardName != null ? boardName : "Доска");

        adapter = new IssueAdapter(issue -> {
            Bundle navArgs = new Bundle();
            navArgs.putString("issueId", issue.getId());
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_issueListFragment_to_issueDetailFragment, navArgs);
        });

        binding.issueRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.issueRecycler.setAdapter(adapter);

        viewModel.getIssues().observe(getViewLifecycleOwner(), issues -> adapter.submitList(issues));
        viewModel.loadIssues(boardId);

        binding.addIssueButton.setOnClickListener(v -> showCreateIssueDialog());
    }

    private void showCreateIssueDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_create_issue, null);
        EditText titleInput = dialogView.findViewById(R.id.issueTitleInput);
        EditText descInput = dialogView.findViewById(R.id.issueDescInput);
        EditText priorityInput = dialogView.findViewById(R.id.issuePriorityInput);

        new AlertDialog.Builder(requireContext())
                .setTitle("Новая задача")
                .setView(dialogView)
                .setPositiveButton("Создать", (dialog, which) -> {
                    String title = titleInput.getText().toString();
                    String description = descInput.getText().toString();
                    String priority = priorityInput.getText().toString();
                    if (TextUtils.isEmpty(title)) {
                        Toast.makeText(requireContext(), "Название обязательно", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    User user = viewModel.getCurrentUser().getValue();
                    String assignee = user != null ? user.getName() : "Unassigned";
                    viewModel.createIssue(boardId, title, description, priority.isEmpty() ? "Medium" : priority, assignee);
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}


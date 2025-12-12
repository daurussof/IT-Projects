package com.example.jira.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jira.MainViewModel;
import com.example.jira.R;
import com.example.jira.data.model.Issue;
import com.example.jira.databinding.FragmentIssueDetailBinding;
import com.example.jira.ui.adapters.CommentAdapter;

public class IssueDetailFragment extends Fragment {

    private FragmentIssueDetailBinding binding;
    private MainViewModel viewModel;
    private String issueId;
    private CommentAdapter adapter;

    private static final String[] STATUSES = {"К выполнению", "В работе", "На проверке", "Выполнено"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIssueDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        Bundle args = getArguments();
        if (args != null) {
            issueId = args.getString("issueId");
        }
        adapter = new CommentAdapter();
        binding.commentList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.commentList.setAdapter(adapter);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, STATUSES);
        binding.statusSpinner.setAdapter(statusAdapter);

        viewModel.getSelectedIssue().observe(getViewLifecycleOwner(), this::renderIssue);
        viewModel.openIssue(issueId);

        binding.saveStatusButton.setOnClickListener(v -> {
            String status = (String) binding.statusSpinner.getSelectedItem();
            viewModel.updateStatus(issueId, status);
            Toast.makeText(requireContext(), getString(R.string.status_updated), Toast.LENGTH_SHORT).show();
        });

        binding.addCommentButton.setOnClickListener(v -> addComment());
    }

    private void renderIssue(Issue issue) {
        if (issue == null) {
            return;
        }
        binding.issueTitle.setText(issue.getTitle());
        binding.issueDescription.setText(issue.getDescription());
        binding.issueAssignee.setText(issue.getAssignee());
        binding.issuePriority.setText(issue.getPriority());
        int statusPosition = java.util.Arrays.asList(STATUSES).indexOf(issue.getStatus());
        if (statusPosition >= 0) {
            binding.statusSpinner.setSelection(statusPosition);
        }
        adapter.submitList(issue.getComments());
    }

    private void addComment() {
        String text = binding.commentInput.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(requireContext(), getString(R.string.enter_comment), Toast.LENGTH_SHORT).show();
            return;
        }
        String author = "System";
        if (viewModel.getCurrentUser().getValue() != null) {
            author = viewModel.getCurrentUser().getValue().getName();
        }
        viewModel.addComment(issueId, author, text);
        binding.commentInput.setText("");
    }
}


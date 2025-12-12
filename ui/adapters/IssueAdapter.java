package com.example.jira.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jira.R;
import com.example.jira.data.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.IssueViewHolder> {

    public interface OnIssueClick {
        void onIssueClick(Issue issue);
    }

    private final List<Issue> items = new ArrayList<>();
    private final OnIssueClick listener;

    public IssueAdapter(OnIssueClick listener) {
        this.listener = listener;
    }

    public void submitList(List<Issue> issues) {
        items.clear();
        if (issues != null) {
            items.addAll(issues);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_issue, parent, false);
        return new IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
        Issue issue = items.get(position);
        holder.title.setText(issue.getTitle());
        holder.subtitle.setText(issue.getDescription());
        holder.status.setText(issue.getStatus());
        holder.priority.setText(issue.getPriority());
        holder.itemView.setOnClickListener(v -> listener.onIssueClick(issue));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class IssueViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;
        TextView status;
        TextView priority;

        IssueViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.issueTitle);
            subtitle = itemView.findViewById(R.id.issueSubtitle);
            status = itemView.findViewById(R.id.issueStatus);
            priority = itemView.findViewById(R.id.issuePriority);
        }
    }
}


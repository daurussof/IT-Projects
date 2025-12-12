package com.example.jira.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jira.R;
import com.example.jira.data.model.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final List<Comment> items = new ArrayList<>();
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("ru", "RU"));

    public void submitList(List<Comment> comments) {
        items.clear();
        if (comments != null) {
            items.addAll(comments);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = items.get(position);
        holder.author.setText(comment.getAuthor());
        holder.message.setText(comment.getMessage());
        holder.date.setText(formatter.format(new Date(comment.getCreatedAt())));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView message;
        TextView date;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.commentAuthor);
            message = itemView.findViewById(R.id.commentMessage);
            date = itemView.findViewById(R.id.commentDate);
        }
    }
}


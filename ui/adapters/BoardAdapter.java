package com.example.jira.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jira.R;
import com.example.jira.data.model.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    public interface OnBoardClick {
        void onBoardClick(Board board);
    }

    private final List<Board> items = new ArrayList<>();
    private final OnBoardClick listener;

    public BoardAdapter(OnBoardClick listener) {
        this.listener = listener;
    }

    public void submitList(List<Board> boards) {
        items.clear();
        if (boards != null) {
            items.addAll(boards);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        return new BoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        Board board = items.get(position);
        holder.title.setText(board.getName());
        holder.subtitle.setText(board.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onBoardClick(board));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class BoardViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;

        BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.boardTitle);
            subtitle = itemView.findViewById(R.id.boardSubtitle);
        }
    }
}


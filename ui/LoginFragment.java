package com.example.jira.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.jira.MainViewModel;
import com.example.jira.R;
import com.example.jira.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding.loginButton.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = binding.usernameInput.getText().toString();
        String password = binding.passwordInput.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(requireContext(), "Введите логин и пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = viewModel.login(username, password);
        if (success) {
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.action_loginFragment_to_boardListFragment);
        } else {
            Toast.makeText(requireContext(), "Неверные данные: admin/password", Toast.LENGTH_SHORT).show();
        }
    }
}


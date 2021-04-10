package com.openu.sadna.booklibrary.ui.loginActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.util.InjectorUtils;

public class RegisterFragment extends Fragment {


    private LoginViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), InjectorUtils.provideLoginViewModelFactory(requireActivity().getApplication())).get(LoginViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final EditText firstNameEditText = view.findViewById(R.id.fname);
        final EditText lastNameEditText = view.findViewById(R.id.lname);
        final EditText usernameEditText = view.findViewById(R.id.username);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final EditText repeatPasswordEditText = view.findViewById(R.id.password_repeat);
        final Button registerButton = view.findViewById(R.id.register);

        repeatPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                    if(viewModel != null && viewModel.getIsLoading().getValue() != null && !viewModel.getIsLoading().getValue())
                        viewModel.register(usernameEditText.getText().toString(),
                                passwordEditText.getText().toString(),
                                repeatPasswordEditText.getText().toString(),
                                firstNameEditText.getText().toString(),
                                lastNameEditText.getText().toString());
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewModel != null&& viewModel.getIsLoading().getValue() != null && !viewModel.getIsLoading().getValue()) {
                    viewModel.register(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            repeatPasswordEditText.getText().toString(),
                            firstNameEditText.getText().toString(),
                            lastNameEditText.getText().toString());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel = null;
    }
}
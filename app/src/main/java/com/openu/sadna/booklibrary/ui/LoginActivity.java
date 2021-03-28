package com.openu.sadna.booklibrary.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Resource;
import com.openu.sadna.booklibrary.data.model.User;
import com.openu.sadna.booklibrary.util.InjectorUtils;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, InjectorUtils.provideLoginViewModelFactory()).get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginResult().observe(this, new Observer<Resource<User>>() {
            @Override
            public void onChanged(@Nullable  Resource<User> loginResult) {
                if (loginResult == null)
                    return;
                loadingProgressBar.setVisibility(View.GONE);

                if (!loginResult.isSuccessful())
                    showLoginFailed();
                else if (loginResult.getData() != null)
                    onLogin(loginResult.getData());
            }
        });


        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void onLogin(User user) {
        String welcome = getString(R.string.welcome) + user.getFirstName() + " " + user.getLastName() ;
        Toast.makeText(this, welcome, Toast.LENGTH_LONG).show();
        //TODO go to catalog activity
    }

    private void showLoginFailed() {
        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT).show();
    }
}
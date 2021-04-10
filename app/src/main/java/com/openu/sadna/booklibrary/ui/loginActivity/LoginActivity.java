package com.openu.sadna.booklibrary.ui.loginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.network.pojo.User;
import com.openu.sadna.booklibrary.ui.booksCatalogActivity.BooksCatalogActivity;
import com.openu.sadna.booklibrary.util.InjectorUtils;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private LoginPagerAdapter loginPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = new ViewModelProvider(this, InjectorUtils.provideLoginViewModelFactory(getApplication())).get(LoginViewModel.class);

        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        viewModel.getShowError().observe(this, new Observer<Event<Integer>>() {
            @Override
            public void onChanged(@Nullable  Event<Integer> event) {
                if (event != null && !event.hasBeenHandled())
                    Toast.makeText(LoginActivity.this, event.getContentIfNotHandled(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getCurrentUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null)
                    onLogin(user);
            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading != null && isLoading){
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
                else{
                    loadingProgressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

            }
        });

        ViewPager2 viewPager = findViewById(R.id.pager);
        loginPagerAdapter = new LoginPagerAdapter(this);
        viewPager.setAdapter(loginPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                       switch (position){
                           case 0:
                               tab.setText(R.string.tab_login);
                           case 1:
                               tab.setText(R.string.tab_register);
                       }
                    }
                }
        ).attach();
    }

    private void onLogin(User user) {
        String text = String.format(getResources().getString(R.string.welcome_message), user.getFirstName(), user.getLastName());
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        Intent intent  = new Intent(this, BooksCatalogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    static class LoginPagerAdapter extends FragmentStateAdapter {

        public LoginPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 1)
                return new RegisterFragment();
            return new LoginFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
package com.example.codepathparstagram;

import android.content.Intent;
import android.os.Bundle;

import com.example.codepathparstagram.ui.login.LoginActivity;
import com.example.codepathparstagram.ui.login.LoginViewModel;
import com.example.codepathparstagram.ui.login.LoginViewModelFactory;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.codepathparstagram.databinding.ActivityFeedBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private ActivityFeedBinding binding;
    private LoginViewModel loginViewModel;
    List<Post> posts;
    PostAdapter adapter;
    RecyclerView rvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        rvPosts = binding.rvPosts;
        posts = new ArrayList<>();
        adapter = new PostAdapter(this, posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(layoutManager);
        rvPosts.setAdapter(adapter);

        queryPosts();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == R.id.miCompose)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.miProfile)
        {
            loginViewModel.logout();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null)
                {
                    Log.e("FeedActivity","Issue with getting posts", e);
                    return;
                }
                for (Post post : posts)
                {
                    Log.i("FeedActivity", "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                    adapter.addAll(posts);
                }
            }
        });
    }
}
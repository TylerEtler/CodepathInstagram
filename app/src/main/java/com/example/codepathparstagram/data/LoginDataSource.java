package com.example.codepathparstagram.data;

import android.util.Log;

import com.example.codepathparstagram.data.model.LoggedInUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    LoggedInUser returnUser = null;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            //final LoggedInUser[] returnUser = new LoggedInUser[1];
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e != null)
                    {
                        Log.e("LoginDataSource", "Issue with login", e);
                        return;
                    }
                    setReturnUser(username);
                }
            });
            if (returnUser == null)
            {
                return new Result.Error(new IOException("Error logging in"));
            }
            return new Result.Success<>(returnUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    private void setReturnUser(String username)
    {
        returnUser = new LoggedInUser(java.util.UUID.randomUUID().toString(),
                username);
    }

    public void logout() {
        // TODO: revoke authentication
        ParseUser.logOut();

    }

    public Result<LoggedInUser> signup(String username, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        try {
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("LoginDataSource", "Issue with login", e);
                    return;
                }
                setReturnUser(username);
            }
        });
            return new Result.Success<>(returnUser);
        } catch (Exception e)
            {
                return new Result.Error(new IOException("Error signing up", e));
            }

    }
}
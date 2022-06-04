package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.databinding.ActivityComposeBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import es.dmoral.toasty.Toasty;
import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String TAG = "ComposeActivity";

    TwitterClient client;

    private ActivityComposeBinding binding;
    boolean isReply = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = ActivityComposeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = TwitterApp.getRestClient(this);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("Tweet"));
        if (tweet != null) {
            isReply = true;
            binding.etCompose.setText("@" + tweet.user.screenName + " ");
        } else { isReply = false; }

        // Add on click listener to publish
        binding.btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = binding.etCompose.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toasty.error(ComposeActivity.this,"Sorry, your tweet cannot be empty.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Toasty.error(ComposeActivity.this, "Sorry, your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }
                // Toast.makeText(ComposeActivity.this, tweetContent, Toast.LENGTH_LONG).show();

                // Make API call to Twitter
                if (isReply) {
                    // Reply to user tweet
                    client.replyTo(tweet.id, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Toasty.success(ComposeActivity.this, "YAY", Toasty.LENGTH_SHORT).show();
                            try {
                                Tweet tweet = Tweet.fromJson(json.jsonObject);
                                Log.i(TAG, "Reply says: " + tweet.body);
                                Intent intent = new Intent();
                                intent.putExtra("tweet", Parcels.wrap(tweet));
                                setResult(RESULT_OK, intent);
                                // closes the activity ,pass data to parent (timeline)
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Toasty.error(ComposeActivity.this, "NAY", Toasty.LENGTH_SHORT).show();
                        }
                    }, tweetContent);
                } else {
                    // Post tweet to timeline
                    client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Toasty.success(ComposeActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
                            Log.i(TAG, "onSuccess to publish Tweet");
                            try {
                                Tweet tweet = Tweet.fromJson(json.jsonObject);
                                Log.i(TAG, "Published Tweet says: " + tweet.body);
                                Intent intent = new Intent();
                                intent.putExtra("tweet", Parcels.wrap(tweet));
                                setResult(RESULT_OK, intent);
                                // closes the activity ,pass data to parent (timeline)
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e(TAG, "onFailure to publish Tweet", throwable);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        // This will request EditText to focus so we can begin typing
        binding.etCompose.requestFocus();
        // Pop up the emulator's keyboard for use
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        super.onResume();
    }
}
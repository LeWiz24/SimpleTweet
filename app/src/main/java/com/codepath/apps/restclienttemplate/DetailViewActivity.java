package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailViewActivity extends AppCompatActivity {
    public static final String TAG = "DetailActivity";
    Tweet tweet;
    TwitterClient client;
    // ActivityDetailBinding binding;
    ImageView ivProfile;
    TextView tvUsername;
    TextView tvHandle;
    TextView tvBody;
    TextView tvDate;
    TextView tvLikesCount;
    TextView tvRetweetCount;
    ImageButton ibReply;
    ImageButton ibRetweet;
    ImageButton ibLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        // binding = ActivityDetailBinding.inflate(getLayoutInflater());
        client = TwitterApp.getRestClient(this);

        tvUsername = findViewById(R.id.tvUsername);
        ivProfile = findViewById(R.id.ivProfile);
        tvHandle = findViewById(R.id.tvHandle);
        tvBody = findViewById(R.id.tvBody);
        tvDate = findViewById(R.id.tvDate);
        tvLikesCount = findViewById(R.id.tvLikesCount);
        tvRetweetCount = findViewById(R.id.tvRetweetCount);


        tweet = Parcels.unwrap(getIntent().getParcelableExtra("Tweet"));
        loadTweet();
    }
        private void loadTweet() {
            tvUsername.setText(tweet.user.screenName);
            tvHandle.setText("@" + tweet.user.name);
            Glide.with(this)
                    .load(tweet.user.profileImageUrl)
                    .circleCrop()
                    .into(ivProfile);

            tvBody.setText(tweet.body);
            tvDate.setText(formattedDate(tweet.createdAt));
            if (tweet.likesCount != 0) {
                tvLikesCount.setVisibility(View.VISIBLE);
                tvLikesCount.setText(String.valueOf(tweet.likesCount) + " Likes");
            }
            if (tweet.retweetCount != 0) {
                tvRetweetCount.setVisibility(View.VISIBLE);
                tvRetweetCount.setText(String.valueOf(tweet.retweetCount) + " Retweets");
            }



        }
        private String formattedDate (String rawDate){
            StringBuilder result = new StringBuilder();
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
            Date date = new Date(rawDate);
            result.append(formatter.format(date));
            formatter = new SimpleDateFormat("MM/dd/yyyy");
            result.append(" - " + formatter.format(date));
            return result.toString();
        }
    }

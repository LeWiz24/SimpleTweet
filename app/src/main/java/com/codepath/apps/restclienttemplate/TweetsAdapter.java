package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;

import java.util.List;

// Pass in context and list of tweets.  AS helps implement this

// For each row inflate layout          AS helps implement this

// Bind values based on position        AS helps implement this

// Define viewholder, usually starting point?
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    // Pass in context and list of tweets
    Context context;
    List<Tweet> tweets;


    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // Inflates the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // Binds data based on position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Need to get data at position
        Tweet tweet = tweets.get(position);
        // Bind the tweet to viewholder
        holder.bind(tweet);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo: Add detailed view
                Intent i = new Intent(context, DetailViewActivity.class);
                i.putExtra("Tweet", Parcels.wrap(tweet));
                context.startActivity(i);

            }
        });
    }
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweets) {
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Declares all the fields we will be populating with data

        ImageView ivProfileImage;
        TextView tvScreenName;
        TextView tvHandle;
        TextView tvBody;
        TextView tvCreatedAt;
        ImageView ivUrl;
        ImageButton ibLikes;
        TextView likesCount;
        TextView retweetCount;
        TextView replyCount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Makes connection for the items on XML file that these attributes will fill
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvBody = itemView.findViewById(R.id.tvBody);
            ivUrl = itemView.findViewById(R.id.ivUrl);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            ibLikes = itemView.findViewById(R.id.ibLikes);
            likesCount = itemView.findViewById(R.id.tvLikesCount);
            retweetCount = itemView.findViewById(R.id.tvRetweetCount);
            replyCount = itemView.findViewById(R.id.tvRepliesCount);

        }

        public void bind(Tweet tweet) {
            // Sets the attribute
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvHandle.setText("@" + tweet.user.name);
            tvCreatedAt.setText(tweet.getRelativeTimeAgo(tweet.createdAt));
            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .transform(new RoundedCorners(75))
                    .into(ivProfileImage);
            if (!tweet.mediaUrl.equals("")) {
                ivUrl.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(tweet.mediaUrl)
                        .centerCrop()
                        .transform(new RoundedCorners(20))
                        .into(ivUrl);
            } else {
                ivUrl.setVisibility(View.GONE);
            }

            if (tweet.likesCount != 0) {
                likesCount.setVisibility(View.VISIBLE);
                likesCount.setText(String.valueOf(tweet.likesCount));
            }
            if (tweet.retweetCount !=0 ) {
                retweetCount.setVisibility(View.VISIBLE);
                retweetCount.setText(String.valueOf(tweet.retweetCount));
            }


//            //if (tweet.favoriteTweet == true) {
//            ibLikes.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (favorited) {
//                        ibLikes.setImageResource(R.drawable.ic_vector_heart);
//                        favorited = true;
//                    } else {
//                        ibLikes.setImageResource(R.drawable.ic_vector_heart_stroke);
//                        favorited = false;
//                    }
//                }
//            });

        }
    }
}

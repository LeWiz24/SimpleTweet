package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

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

    }
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Tweet> tweets){
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfileImage;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvTimeStamp;
        TextView tvDot;
        ImageView ivUrl;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvBody = itemView.findViewById(R.id.tvBody);
            ivUrl = itemView.findViewById(R.id.ivUrl);
            // tvTimeStamp = itemView.findViewById(R.id.tvTimestamp);
            // tvDot = itemView.findViewById(R.id.tvDot);
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .transform(new RoundedCorners(43))
                    .into(ivProfileImage);
            if (tweet.mediaUrl != "") {
                ivUrl.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(tweet.mediaUrl)
                        .fitCenter()
                        .override(640, 360)
                        .transform(new RoundedCorners(25))
                        .into(ivUrl);
            } else {
                ivUrl.setVisibility(View.GONE);
            }

        }
    }
}

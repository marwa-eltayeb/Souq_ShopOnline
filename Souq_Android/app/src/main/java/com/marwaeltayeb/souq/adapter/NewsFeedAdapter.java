package com.marwaeltayeb.souq.adapter;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.NewsfeedListItemBinding;
import com.marwaeltayeb.souq.model.NewsFeed;

import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>{

    private final Context mContext;
    private final List<NewsFeed> newsFeedList;

    public NewsFeedAdapter(Context mContext, List<NewsFeed> newsFeedList) {
        this.mContext = mContext;
        this.newsFeedList = newsFeedList;
    }

    @NonNull
    @Override
    public NewsFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        NewsfeedListItemBinding newsfeedListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.newsfeed_list_item,parent,false);
        return new NewsFeedViewHolder(newsfeedListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedViewHolder holder, int position) {
        NewsFeed currentNewsFeed = newsFeedList.get(position);

        // Load poster into ImageView
        String posterUrl = LOCALHOST + currentNewsFeed.getImage().replaceAll("\\\\", "/");
        Glide.with(mContext)
                .load(posterUrl)
                .into(holder.binding.poster);
    }

    @Override
    public int getItemCount() {
        if (newsFeedList == null) {
            return 0;
        }
        return newsFeedList.size();
    }

    static class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        private final NewsfeedListItemBinding binding;

        private NewsFeedViewHolder(NewsfeedListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

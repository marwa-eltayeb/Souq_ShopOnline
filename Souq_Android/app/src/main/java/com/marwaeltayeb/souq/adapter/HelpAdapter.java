package com.marwaeltayeb.souq.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.HelpListItemBinding;
import com.marwaeltayeb.souq.model.Help;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpCenterHolder>{

    private final List<Help> helpList;

    public HelpAdapter(List<Help> helpList) {
        this.helpList = helpList;
    }

    @NonNull
    @Override
    public HelpCenterHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        HelpListItemBinding helpListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.help_list_item,parent,false);
        return new HelpCenterHolder(helpListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpCenterHolder holder, int position) {
        Help currentHelp = helpList.get(position);
        holder.binding.txtQuestion.setText(currentHelp.getQuestion());
        holder.binding.txtAnswer.setText(String.valueOf(currentHelp.getAnswer()));

    }

    @Override
    public int getItemCount() {
        if (helpList == null) {
            return 0;
        }
        return helpList.size();
    }

    static class HelpCenterHolder extends RecyclerView.ViewHolder{

        private final HelpListItemBinding binding;

        private HelpCenterHolder(HelpListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

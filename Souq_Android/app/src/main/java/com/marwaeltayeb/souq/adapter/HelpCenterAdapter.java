package com.marwaeltayeb.souq.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.HelpCenterListItemBinding;
import com.marwaeltayeb.souq.model.Help;

import java.util.List;

public class HelpCenterAdapter extends RecyclerView.Adapter<HelpCenterAdapter.HelpCenterHolder>{

    private List<Help> helpList;

    public HelpCenterAdapter(List<Help> helpList) {
        this.helpList = helpList;
    }

    @NonNull
    @Override
    public HelpCenterHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        HelpCenterListItemBinding helpCenterListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.help_center_list_item,parent,false);
        return new HelpCenterHolder(helpCenterListItemBinding);
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

    class HelpCenterHolder extends RecyclerView.ViewHolder{

        private final HelpCenterListItemBinding binding;

        private HelpCenterHolder(HelpCenterListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

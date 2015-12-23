package com.k_beta.exercise.customviewspractice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dongkai on 2015/12/4.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private ArrayList<String> listData = new ArrayList<>();
    private OnRecycleViewItemClickListener callBack ;
    public MainListAdapter(ArrayList<String> data){
        this.listData.clear();
        if(data != null){
            listData.addAll(data);
        }
    }

    public void setCallBack(OnRecycleViewItemClickListener callBack) {
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(listData.get(position));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack != null){
                    callBack.OnViewItemClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView title ;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public interface OnRecycleViewItemClickListener{
        void OnViewItemClicked(int position);
    }
}

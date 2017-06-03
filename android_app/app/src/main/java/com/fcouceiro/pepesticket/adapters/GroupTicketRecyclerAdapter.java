package com.fcouceiro.pepesticket.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fcouceiro.pepesticket.activities.GroupTicketDetailsActivity;
import com.fcouceiro.pepesticket.R;
import com.fcouceiro.pepesticket.communication.models.GroupTicket;
import com.fcouceiro.pepesticket.communication.models.Ticket;
import com.fcouceiro.pepesticket.components.GroupTicketView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaopedro on 03-06-2017.
 */


public class GroupTicketRecyclerAdapter extends RecyclerView.Adapter<GroupTicketRecyclerAdapter.GroupTicketHolder> {

    private List<GroupTicket> groupTicket;

    public GroupTicketRecyclerAdapter() {
        this.groupTicket = new ArrayList<>();
    }

    public void addItems(List<GroupTicket> items) {
        groupTicket.addAll(items);
        notifyDataSetChanged();
    }


    public long removeItem(int position){
        GroupTicket removedItem = groupTicket.remove(position);
        notifyItemRemoved(position);

        return removedItem.getId();
    }

    public void clear(){
        groupTicket.clear();
        notifyDataSetChanged();
    }

    @Override
    public GroupTicketRecyclerAdapter.GroupTicketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_ticket_recyclerview_item_row, parent, false);
        return new GroupTicketHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(GroupTicketRecyclerAdapter.GroupTicketHolder holder, int position) {
        GroupTicket item = groupTicket.get(position);
        holder.bindGroupTicket(item);
    }

    public int getItemCount() {
        return groupTicket.size();
    }

    public static class GroupTicketHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private GroupTicketView groupTicketView;

        private GroupTicket groupTicket;

        public GroupTicketHolder(View v){
            super(v);

            groupTicketView = (GroupTicketView) v;

            v.setOnClickListener(this);
        }

        public void bindGroupTicket(GroupTicket group) {
            groupTicket = group;
            groupTicketView.setGroupTicket(groupTicket);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent showGroupTicketDetailsIntent = new Intent(context, GroupTicketDetailsActivity.class);
            showGroupTicketDetailsIntent.putExtra(GroupTicketDetailsActivity.EXTRA_GROUP_TICKET_ID, groupTicket.getId());
            context.startActivity(showGroupTicketDetailsIntent);
        }
    }
}



package com.fcouceiro.pepesticket;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fcouceiro.pepesticket.communication.models.GroupTicket;
import com.fcouceiro.pepesticket.communication.models.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaopedro on 03-06-2017.
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.GroupTicketHolder> {

    private List<GroupTicket> groupTicket;

    public RecyclerAdapter() {
        this.groupTicket = new ArrayList<>();
    }

    public void addItems(List<GroupTicket> items) {
        groupTicket.addAll(items);
        notifyDataSetChanged();
    }

    public void clear(){
        groupTicket.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerAdapter.GroupTicketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row, parent, false);
        return new GroupTicketHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.GroupTicketHolder holder, int position) {
        GroupTicket item = groupTicket.get(position);
        holder.bindGroupTicket(item);
    }

    public int getItemCount() {
        return groupTicket.size();
    }

    public static class GroupTicketHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView item;

        public GroupTicketHolder(View v) {
            super(v);

            item = (TextView) v.findViewById(R.id.group_ticket_item);

            v.setOnClickListener(this);
        }

        public void bindGroupTicket(GroupTicket group) {

            String serviceNames = "";
            for (Ticket ticket : group.getTicket()) {
                serviceNames += ticket.getService().getName() + ", ";
            }

            item.setText(serviceNames);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent showGroupTicketDetailsIntent = new Intent(context, GroupTicketDetailsActivity.class);
            //showGroupTicketDetailsIntent.putExtra();
            context.startActivity(showGroupTicketDetailsIntent);
        }
    }
}



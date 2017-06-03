package com.fcouceiro.pepesticket.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fcouceiro.pepesticket.R;
import com.fcouceiro.pepesticket.communication.models.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaopedro on 03-06-2017.
 */

public class GroupTicketDetailsRecyclerAdapter extends RecyclerView.Adapter<GroupTicketDetailsRecyclerAdapter.TicketHolder> {

    private List<Ticket> tickets;

    public GroupTicketDetailsRecyclerAdapter(){
        this.tickets = new ArrayList<>();
    }

    public void addItems(List<Ticket> items) {
        tickets.addAll(items);
        notifyDataSetChanged();
    }

    public void clear(){
        tickets.clear();
        notifyDataSetChanged();
    }

    @Override
    public TicketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_ticket_details_recyclerview_item_row, parent, false);
        return new TicketHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TicketHolder holder, int position) {
        Ticket item = tickets.get(position);
        holder.bindTicket(item);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class TicketHolder extends RecyclerView.ViewHolder {

        private TextView serviceTextView;

        private TextView ticketTextView;

        private TextView actualTicketView;

        private Ticket ticketItem;


        public TicketHolder(View itemView) {
            super(itemView);

            serviceTextView = (TextView) itemView.findViewById(R.id.ticket_service);
            ticketTextView = (TextView) itemView.findViewById(R.id.ticket_number);
            actualTicketView = (TextView) itemView.findViewById(R.id.ticket_actual_number);
        }

        public void bindTicket(Ticket item){
            ticketItem = item;

            serviceTextView.setText(ticketItem.getService().getName());
            ticketTextView.setText(String.valueOf(ticketItem.getTicketNumber()));
            if(ticketItem.getService().getActualTicket() != null){
                actualTicketView.setText(String.format("(%d)", ticketItem.getService().getActualTicket().getTicketNumber()));
            }
            else{
                actualTicketView.setText("(n/a)");
            }
        }
    }
}

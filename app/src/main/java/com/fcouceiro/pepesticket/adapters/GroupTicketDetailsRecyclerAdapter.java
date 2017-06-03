package com.fcouceiro.pepesticket.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fcouceiro.pepesticket.R;
import com.fcouceiro.pepesticket.communication.models.Ticket;

import java.util.ArrayList;

/**
 * Created by joaopedro on 03-06-2017.
 */

public class GroupTicketDetailsRecyclerAdapter extends RecyclerView.Adapter<GroupTicketDetailsRecyclerAdapter.TicketHolder> {

    private ArrayList<Ticket> tickets;


    public static class TicketHolder extends RecyclerView.ViewHolder {

        private TextView serviceTextView;

        private TextView currentTicketTextView; // corresponde ao número atual da senha de um determinado serviço

        private TextView actualTicketview; // corresponde ao número da senha que calha ao utilizador

        private Ticket ticketItem;


        public TicketHolder(View itemView) {
            super(itemView);

            serviceTextView = (TextView) itemView.findViewById(R.id.ticket_service);
            currentTicketTextView = (TextView) itemView.findViewById(R.id.ticket_current_number);
            actualTicketview = (TextView) itemView.findViewById(R.id.ticket_actual_number);
        }

        public void bindTicket(Ticket item){

            ticketItem = item;

            serviceTextView.setText(ticketItem.getService().getName());
            actualTicketview.setText(String.valueOf(ticketItem.getTicketNumber()));
            //TODO: SACAR O NÚMERO ATUAL DA SENHA

        }
    }

    public GroupTicketDetailsRecyclerAdapter(ArrayList<Ticket> items){
        this.tickets = items;
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
}

package com.fcouceiro.pepesticket.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fcouceiro.pepesticket.R;
import com.fcouceiro.pepesticket.communication.models.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscocouceiro on 03/06/17.
 */

public class PrinterRecyclerAdapter extends RecyclerView.Adapter<PrinterRecyclerAdapter.ServiceHolder> {

    private List<Service> services;

    public PrinterRecyclerAdapter() {
        this.services = new ArrayList<>();
    }

    public void addItems(List<Service> items) {
        services.addAll(items);
        notifyDataSetChanged();
    }

    public List<String> getSelectedServicesNames(){
        List<String> names = new ArrayList<>();

        for (Service service : services) {
            if(service.isChecked()){
                names.add(service.getName());
            }
        }

        return names;
    }

    public void clear() {
        services.clear();
        notifyDataSetChanged();
    }

    @Override
    public PrinterRecyclerAdapter.ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.printer_recyclerview_item_row, parent, false);
        return new PrinterRecyclerAdapter.ServiceHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PrinterRecyclerAdapter.ServiceHolder holder, int position) {
        Service item = services.get(position);
        holder.bindGroupTicket(item);
    }

    public int getItemCount() {
        return services.size();
    }

    public static class ServiceHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        private TextView label;

        private CheckBox checkBox;

        private Service service;

        public ServiceHolder(View v) {
            super(v);

            label = (TextView) v.findViewById(R.id.label);
            checkBox = (CheckBox) v.findViewById(R.id.checkbox);
            checkBox.setOnCheckedChangeListener(this);
        }

        public void bindGroupTicket(Service service) {
            this.service = service;

            label.setText(service.getName());
            checkBox.setChecked(service.isChecked());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            service.setChecked(isChecked);
        }
    }
}

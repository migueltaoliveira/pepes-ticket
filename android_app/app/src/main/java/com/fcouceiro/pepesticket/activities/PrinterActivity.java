package com.fcouceiro.pepesticket.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fcouceiro.pepesticket.R;
import com.fcouceiro.pepesticket.adapters.GroupTicketRecyclerAdapter;
import com.fcouceiro.pepesticket.adapters.PrinterRecyclerAdapter;
import com.fcouceiro.pepesticket.communication.API;
import com.fcouceiro.pepesticket.communication.models.GroupTicket;
import com.fcouceiro.pepesticket.communication.models.Service;
import com.fcouceiro.pepesticket.communication.models.ServiceListWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrinterActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private PrinterRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);

        // Setup UI
        setupUI();

        // Request services
        API.getService().getServices().enqueue(servicesCallback);
    }

    private void setupUI() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PrinterRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn_get_ticket).setOnClickListener(this);
    }

    private Callback<List<Service>> servicesCallback = new Callback<List<Service>>() {
        @Override
        public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
            if (!response.isSuccessful()) {
                Toast.makeText(getBaseContext(), getString(R.string.services_not_available), Toast.LENGTH_LONG).show();
                return;
            }

            // Update list
            adapter.addItems(response.body());
        }

        @Override
        public void onFailure(Call<List<Service>> call, Throwable t) {
            Toast.makeText(getBaseContext(), getString(R.string.services_not_available), Toast.LENGTH_LONG).show();
        }
    };

    private Callback<GroupTicket> createGroupTicketCallback = new Callback<GroupTicket>() {
        @Override
        public void onResponse(Call<GroupTicket> call, Response<GroupTicket> response) {
            if(!response.isSuccessful()){
                if (!response.isSuccessful()) {
                    if(adapter.getSelectedServicesNames().size() == 0) {
                        Toast.makeText(getBaseContext(), getString(R.string.select_one_service), Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getBaseContext(), getString(R.string.failed_to_generate_ticket), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
            }

            setResult(RESULT_OK);
            finish();
        }

        @Override
        public void onFailure(Call<GroupTicket> call, Throwable t) {
            Toast.makeText(getBaseContext(), getString(R.string.failed_to_generate_ticket), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_get_ticket){
            API.getService().generateGroupTicket(
                    ServiceListWrapper.fromList(
                            adapter.getSelectedServicesNames()
                    )
            ).enqueue(createGroupTicketCallback);
        }
    }
}

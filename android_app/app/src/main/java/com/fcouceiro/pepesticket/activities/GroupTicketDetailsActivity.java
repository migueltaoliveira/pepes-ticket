package com.fcouceiro.pepesticket.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.fcouceiro.pepesticket.R;
import com.fcouceiro.pepesticket.adapters.GroupTicketDetailsRecyclerAdapter;
import com.fcouceiro.pepesticket.communication.API;
import com.fcouceiro.pepesticket.communication.models.GroupTicket;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by joaopedro on 03-06-2017.
 */
public class GroupTicketDetailsActivity extends AppCompatActivity {

    // Extra id
    public static final String EXTRA_GROUP_TICKET_ID = "GroupTicketDetailsActivity.EXTRA_GROUP_TICKET_ID";

    // UI references
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GroupTicketDetailsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_ticket_detail);

        // Set ui
        setupUI();

        // Fetch group tickets
        long requestedId = getIntent().getLongExtra(EXTRA_GROUP_TICKET_ID, -1L);

        // Get back if no argument was passed
        if(requestedId == -1){
            Toast.makeText(this, R.string.failed_to_fetch_group_tickets, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Request data
        API.getService().getGroupTicket(requestedId).enqueue(groupTicketCallback);
    }

    private void setupUI() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new GroupTicketDetailsRecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private Callback<GroupTicket> groupTicketCallback = new Callback<GroupTicket>() {
        @Override
        public void onResponse(Call<GroupTicket> call, Response<GroupTicket> response) {
            if(!response.isSuccessful()){
                Toast.makeText(getBaseContext(), R.string.failed_to_fetch_group_tickets, Toast.LENGTH_SHORT).show();
                return;
            }

            // Add items to the adapter
            adapter.clear();
            adapter.addItems(response.body().getTickets());
        }

        @Override
        public void onFailure(Call<GroupTicket> call, Throwable t) {
            Toast.makeText(getBaseContext(), R.string.failed_to_fetch_group_tickets, Toast.LENGTH_SHORT).show();
        }
    };
}

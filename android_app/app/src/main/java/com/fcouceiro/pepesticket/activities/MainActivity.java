package com.fcouceiro.pepesticket.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.fcouceiro.pepesticket.R;
import com.fcouceiro.pepesticket.adapters.GroupTicketRecyclerAdapter;
import com.fcouceiro.pepesticket.communication.API;
import com.fcouceiro.pepesticket.communication.AuthenticationInterceptor;
import com.fcouceiro.pepesticket.communication.models.GroupTicket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RestTestActivity";

    // Request codes
    private static final int RC_PRINT_TICKET = 0x23;

    private SharedPreferences sharedPref;

    // UI references
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GroupTicketRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set ui
        setupUI();

        // Get preferences
        sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // Inflate user id
        String userId = inflateUserId();

        // Initialize REST service
        API.initialize(userId);

        // Check if user has an identification already (request otherwise)
        if (userId == null) {
            API.getService().authenticate().enqueue(authenticationCallback);
        } else {
            API.getService().getGroupTickets().enqueue(groupTicketsCallback);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void setupUI() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new GroupTicketRecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private String inflateUserId() {
        return sharedPref.getString(getString(R.string.preference_user_id_key), null);
    }

    private void persistUserId(String userId) {
        sharedPref.edit()
                .putString(
                        getString(R.string.preference_user_id_key),
                        userId
                )
                .commit();
    }

    private Callback<String> authenticationCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if (!response.isSuccessful()) {
                Toast.makeText(getBaseContext(), getString(R.string.authentication_failed), Toast.LENGTH_LONG).show();
                return;
            }

            String userId = response.body();

            // Persists user Id
            persistUserId(userId);

            // Use it in future requests
            AuthenticationInterceptor.getInstance().setUserId(userId);
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            Toast.makeText(getBaseContext(), getString(R.string.authentication_failed), Toast.LENGTH_LONG).show();
        }
    };

    private Callback<List<GroupTicket>> groupTicketsCallback = new Callback<List<GroupTicket>>() {
        @Override
        public void onResponse(Call<List<GroupTicket>> call, Response<List<GroupTicket>> response) {
            if (!response.isSuccessful()) {
                Toast.makeText(getBaseContext(), getString(R.string.group_tickets_not_available), Toast.LENGTH_LONG).show();
                return;
            }

            // Add items to the adapter
            adapter.clear();
            adapter.addItems(response.body());
        }

        @Override
        public void onFailure(Call<List<GroupTicket>> call, Throwable t) {
            Toast.makeText(getBaseContext(), getString(R.string.group_tickets_not_available), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            Intent intent = new Intent(this, PrinterActivity.class);
            startActivityForResult(intent, RC_PRINT_TICKET);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PRINT_TICKET) {
            if(resultCode == RESULT_OK) {
                // Request new group tickets
                API.getService().getGroupTickets().enqueue(groupTicketsCallback);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

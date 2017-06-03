package com.fcouceiro.pepesticket.components;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fcouceiro.pepesticket.R;
import com.fcouceiro.pepesticket.communication.models.GroupTicket;
import com.fcouceiro.pepesticket.communication.models.Ticket;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by franciscocouceiro on 03/06/17.
 */

public class GroupTicketView extends LinearLayout {

    private static final String TAG = "GroupTicketView";

    private static final int MAX_CHARS = 12;

    // Our group ticket
    private GroupTicket groupTicket;

    public GroupTicketView(Context context) {
        super(context);
        initialize(context);
    }

    public GroupTicketView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public GroupTicketView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GroupTicketView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }


    private void initialize(Context context) {
        // Configure view style
        setOrientation(HORIZONTAL);
    }

    public void setGroupTicket(GroupTicket groupTicket) {
        this.groupTicket = groupTicket;

        if (groupTicket == null) {
            return;
        }

        // Remove children
        if (getChildCount() > 0) {
            removeViews(0, getChildCount());
        }

        // Create params
        int margin = (int) getResources().getDimension(R.dimen.default_margin);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(margin, margin, margin, margin);

        // Create one view for each
        List<Ticket> tickets = groupTicket.getTickets();

        int childCount = computeNumberOfChilds(tickets);

        for(int i=0;i<childCount;i++){
            TicketCompactView ticketCompactView = new TicketCompactView(getContext());
            ticketCompactView.setTicket(tickets.get(i));
            ticketCompactView.setPadding(margin, margin, margin, margin);
            addView(ticketCompactView, params);
        }

        // Add text view
        TextView lblPlus = new TextView(getContext());
        lblPlus.setGravity(Gravity.CENTER_VERTICAL);
        lblPlus.setTextSize(getResources().getDimension(R.dimen.font_xs));
        lblPlus.setText("+" + String.valueOf(tickets.size() - childCount));
        addView(lblPlus, params);
    }

    private int computeNumberOfChilds(List<Ticket> tickets){
        int charCount = 0;
        int childCount = 0;

        Collections.sort(tickets,
        new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                return o1.getService().getName().length() - o2.getService().getName().length();
            }
        });

        for (Ticket ticket : tickets) {
            if(charCount + ticket.getService().getName().length() <= MAX_CHARS){
                charCount += ticket.getService().getName().length();
                childCount++;
            }
        }

        return childCount;
    }
}

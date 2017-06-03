package com.fcouceiro.pepesticket.components;

import android.content.Context;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fcouceiro.pepesticket.R;
import com.fcouceiro.pepesticket.communication.models.Ticket;

/**
 * Created by franciscocouceiro on 03/06/17.
 */

public class TicketCompactView extends LinearLayout {

    private TextView lblTicketServiceName;
    private TextView lblTicketNumber;

    public TicketCompactView(Context context) {
        super(context);
        initialize(context);
    }

    public TicketCompactView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public TicketCompactView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TicketCompactView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    private void initialize(Context context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.ticket_compact_view, this, true);

        // Get UI references
        this.lblTicketServiceName = (TextView) viewGroup.findViewById(R.id.lbl_ticket_service_name);
        this.lblTicketNumber = (TextView) viewGroup.findViewById(R.id.lbl_ticket_number);

        // Configure view style
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.item_background_blue);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(getResources().getDimension(R.dimen.default_margin));
        }
    }

    public void setTicket(Ticket ticket){
        // Set values
        this.lblTicketServiceName.setText(ticket.getService().getName());
        this.lblTicketNumber.setText(String.valueOf(ticket.getTicketNumber()));
    }
}

package cardmanager.ciandt.com.cardmanager.presentation.payment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.Payment;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;

public class PaymentsAdapter extends ArrayAdapter<Payment> {
    public View.OnClickListener removeListener;

    private Context context;
    private int layoutResourceId;
    private ArrayList<Payment> data = null;

    public PaymentsAdapter(Context context, int layoutResourceId, ArrayList<Payment> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PaymentHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PaymentHolder();
            holder.txtName = (TextView)row.findViewById(R.id.schedule_txt_name);
            holder.txtDate = (TextView)row.findViewById(R.id.schedule_txt_date);
            holder.imgRemove = (ImageView)row.findViewById(R.id.schedule_btn_remove);

            row.setTag(holder);
        }
        else
        {
            holder = (PaymentHolder)row.getTag();
        }

        Payment payment = data.get(position);
        holder.txtName.setText(payment.name);
        holder.txtDate.setText(Utils.formatDate(context, payment.date, R.string.schedule_format_date_and_time));

        if (removeListener != null) {
            holder.imgRemove.setOnClickListener(removeListener);
            holder.imgRemove.setTag(payment);
        }

        return row;
    }

    static class PaymentHolder
    {
        TextView txtName;
        TextView txtDate;
        ImageView imgRemove;
    }
}
package cardmanager.ciandt.com.cardmanager.presentation.extracts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.Purchase;

public class PurchaseAdapter extends ArrayAdapter<Purchase> {
    Context context;
    int layoutResourceId;
    ArrayList<Purchase> data = null;

    public PurchaseAdapter(Context context, int layoutResourceId, ArrayList<Purchase> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PurchaseHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PurchaseHolder();
            holder.txtPrice = (TextView)row.findViewById(R.id.txt_purchase_price);
            holder.txtTitle = (TextView)row.findViewById(R.id.txt_purchase_title);
            holder.txtDate = (TextView)row.findViewById(R.id.txt_purchase_date);

            row.setTag(holder);
        }
        else
        {
            holder = (PurchaseHolder)row.getTag();
        }

        Purchase purchase = data.get(position);
        holder.txtPrice.setText(purchase.value);
        holder.txtTitle.setText(purchase.spent);
        holder.txtDate.setText(purchase.date);

        return row;
    }

    static class PurchaseHolder
    {
        TextView txtPrice;
        TextView txtTitle;
        TextView txtDate;
    }
}
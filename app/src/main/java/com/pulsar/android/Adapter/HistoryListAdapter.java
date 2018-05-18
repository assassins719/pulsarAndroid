package com.pulsar.android.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.pulsar.android.Models.HistoryItem;
import com.pulsar.android.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoryListAdapter extends BaseAdapter implements Filterable {

    Context c;
    ArrayList<HistoryItem> mData;
    CustomFilter filter;
    ArrayList<HistoryItem> filterList;
    String []strFees = {"ƒ", "₦","Ʉ"};
    public HistoryListAdapter(Context ctx,ArrayList<HistoryItem> mData) {
        // TODO Auto-generated constructor stub
        this.c=ctx;
        this.mData=mData;
        this.filterList=mData;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public HistoryItem getItem(int pos) {
        // TODO Auto-generated method stub
        return mData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        // TODO Auto-generated method stub
        return mData.indexOf(getItem(pos));
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.history_list_item, null);
        }

        TextView tx_date=(TextView) convertView.findViewById(R.id.tx_date);
        TextView tx_amount=(TextView) convertView.findViewById(R.id.tx_amount);
        EditText edit_id=(EditText) convertView.findViewById(R.id.tx_id);
        TextView tx_usd=(TextView) convertView.findViewById(R.id.tx_amount_usd);


        //SET DATA TO THEM
        long nTime = mData.get(pos).getnTime();
        DateFormat df;
        df = new SimpleDateFormat("MMMM dd,yyyy 'at' HH:mm");
        tx_date.setText(df.format(nTime));
        String strFavels = "";
        if(mData.get(pos).getIsSender() == 3){
            strFavels = "<font color='black'>" + strFees[mData.get(pos).getCardId()] + " </font><font color='green'>+ " + mData.get(pos).getStrAmount() + "</font> ";
        }else{
            strFavels = "<font color='black'>" + strFees[mData.get(pos).getCardId()] + " </font><font color='red'>- " + mData.get(pos).getStrAmount()+ "</font> ";
        }
        tx_amount.setText(Html.fromHtml(strFavels), TextView.BufferType.SPANNABLE);
        edit_id.setText("From: " + mData.get(pos).getStrId());
//        tx_usd.setText(mData.get(pos).getStrUsd());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new CustomFilter();
        }

        return filter;
    }

    //INNER CLASS
    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();

                ArrayList<HistoryItem> filters=new ArrayList<HistoryItem>();

                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getStrId().toUpperCase().contains(constraint))
                    {
                        filters.add(filterList.get(i));
                    }
                }

                results.count=filters.size();
                results.values=filters;

            }else
            {
                results.count=filterList.size();
                results.values=filterList;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub

            mData=(ArrayList<HistoryItem>) results.values;
            notifyDataSetChanged();
        }

    }

}
package com.example.admin.message_search;



import android.content.Context;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class CustomAdapter extends BaseAdapter {
    public ArrayList<Message> newsArrayList;
    Context context;
    String address,date,body;
    public CustomAdapter(Context c,ArrayList<Message> message){
        newsArrayList = message;
        context = c;
    }

    public CustomAdapter(Context c,ArrayList<Message> message,String address,String body,String date){
        newsArrayList = message;
        context = c;
        this.address=address;
        this.body=body;
        this.date=date;
    }
    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
            ViewHolder v = new ViewHolder();
            v.titleTextView = view.findViewById(R.id.tv_news_title);
            v.descriptionTextView = view.findViewById(R.id.tv_news_description);
            v.date=view.findViewById(R.id.date);
            v.type=view.findViewById(R.id.type);
            View ll = view.findViewById(R.id.ll_list_item);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ll.setClipToOutline(true);
            }
            view.setTag(v);
        }


        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.titleTextView.setText(newsArrayList.get(i).getaddress());
        viewHolder.descriptionTextView.setText(newsArrayList.get(i).getbody());
        viewHolder.date.setText(newsArrayList.get(i).getDate());
        viewHolder.type.setText(newsArrayList.get(i).gettype());
        if(address!=null)
            setHighLightedText(viewHolder.titleTextView,address);
        if(body!=null)
            setHighLightedText(viewHolder.descriptionTextView,body);
        if(date!=null)
            setHighLightedText(viewHolder.date,date);

        return view;
    }
    private class ViewHolder{
        TextView titleTextView;
        TextView descriptionTextView;
        TextView date;
        TextView type;
    }

    public void setHighLightedText(TextView tv, String textToHighlight) {
        String tvt = tv.getText().toString().toLowerCase();
        int ofe = tvt.indexOf(textToHighlight.toLowerCase(), 0);
        Spannable wordToSpan = new SpannableString(tv.getText());
        for (int ofs = 0; ofs < tvt.length() && ofe != -1; ofs = ofe + 1) {
            ofe = tvt.indexOf(textToHighlight.toLowerCase(), ofs);
            if (ofe == -1)
                break;
            else {
                wordToSpan.setSpan(new BackgroundColorSpan(0xFFFFFF00), ofe, ofe + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE);
            }
        }
    }
}

package com.example.admin.message_search;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.admin.message_search.MainActivity.adapter;
import static com.example.admin.message_search.MainActivity.contact;
import static com.example.admin.message_search.MainActivity.message;

public class TabFragment1 extends Fragment{

    ArrayList<Message> match;
    ListView list;
    Context ct;
    EditText start,end,pattern,search;
    TextView time;
    View v,v1;
    AutoCompleteTextView name;
    Button find;
    ArrayAdapter<String> adt;
    static Date d,d1;
    AlertDialog ad;
    static int cnt=0;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment1 tabFragment = new TabFragment1();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cnt=0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cnt++;

            v = inflater.inflate(R.layout.search, container, false);
            v1 = inflater.inflate(R.layout.query, container, false);
            ct = v.getContext();

        list = v.findViewById(R.id.list);
        adapter = new CustomAdapter(getContext(), message);
        list.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        search=v.findViewById(R.id.search);
        time=v.findViewById(R.id.time);


        adt=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,contact);
        name=v1.findViewById(R.id.address);
        name.setThreshold(1);
        name.setAdapter(adt);
        pattern=v1.findViewById(R.id.pattern);
        start=v1.findViewById(R.id.start);
        end=v1.findViewById(R.id.end);
        find=v1.findViewById(R.id.find);




        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DATE);
                DatePickerDialog dialog = new DatePickerDialog(v1.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Date temp = new Date(i-1900, i1, i2);
                        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
                        try {
                            d=sdf.parse(DateFormat.getDateInstance(DateFormat.SHORT).format(temp));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        start.setText( DateFormat.getDateInstance(DateFormat.SHORT).format(temp));
                    }
                }, year, month, day);

                dialog.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DATE);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Date temp = new Date(i-1900, i1, i2);
                        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
                        try {
                            d1=sdf.parse(DateFormat.getDateInstance(DateFormat.SHORT).format(temp));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        end.setText( DateFormat.getDateInstance(DateFormat.SHORT).format(temp));
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KMP kmp = new KMP();
                int n=0,p=0,s=-1,e=0;

                if(!name.getText().toString().equals("") || !pattern.getText().toString().equals("") || !start.getText().toString().equals("") || !end.getText().toString().equals("")) {
                    long tStart = System.currentTimeMillis();
                    match=new ArrayList<>();
                    if (!name.getText().toString().equals(""))
                        n=1;
                    if (!pattern.getText().toString().equals(""))
                        p=1;

                    if (end.getText().toString().equals("") && !start.getText().toString().equals("")) {
                        s=1;e=1;
                        Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DATE);
                        Date temp = new Date(year-1900, month, day);
                        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
                        try {
                            d1=sdf.parse(DateFormat.getDateInstance(DateFormat.SHORT).format(temp));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }

                    if (!end.getText().toString().equals("") && start.getText().toString().equals("")) {
                        e = 1;
                        s = 0;
                    }

                    if (!end.getText().toString().equals("") && !start.getText().toString().equals("")) {
                        s = 1;
                        e = 1;
                    }

                    if(n==1) {
                        if (p == 1) {
                            if (s == 1) {
                                for (int i = 0; i < message.size(); i++) {
                                    if (checkDate(message.get(i).getDate1())
                                                && kmp.search(message.get(i).getbody().toLowerCase(), pattern.getText().toString().toLowerCase()) == 1 &&
                                                    kmp.search(message.get(i).getaddress().toLowerCase(), name.getText().toString().toLowerCase()) == 1)
                                            match.add(message.get(i));
                                }
                            }
                            else if(s==0) {
                                for (int i = 0; i < message.size(); i++) {
                                    if (checkDateBefore(message.get(i).getDate1())
                                            && kmp.search(message.get(i).getbody().toLowerCase(), pattern.getText().toString().toLowerCase()) == 1 &&
                                            kmp.search(message.get(i).getaddress().toLowerCase(), name.getText().toString().toLowerCase()) == 1)
                                        match.add(message.get(i));
                                }
                            }
                            else{
                                for (int i = 0; i < message.size(); i++)
                                    if (kmp.search(message.get(i).getbody().toLowerCase(), pattern.getText().toString().toLowerCase()) == 1 &&
                                            kmp.search(message.get(i).getaddress().toLowerCase(), name.getText().toString().toLowerCase()) == 1)
                                        match.add(message.get(i));
                            }

                        }
                        else {
                            if (s == 1) {
                                for (int i = 0; i < message.size(); i++) {
                                    if (checkDate(message.get(i).getDate1()) &&
                                            kmp.search(message.get(i).getaddress().toLowerCase(), name.getText().toString().toLowerCase()) == 1)
                                        match.add(message.get(i));
                                }
                            }
                            else if(s==0) {
                                for (int i = 0; i < message.size(); i++) {
                                    if (checkDateBefore(message.get(i).getDate1()) &&
                                            kmp.search(message.get(i).getaddress().toLowerCase(), name.getText().toString().toLowerCase()) == 1)
                                        match.add(message.get(i));
                                }
                            }
                            else {
                                for (int i = 0; i < message.size(); i++) {
                                    if (kmp.search(message.get(i).getaddress().toLowerCase(), name.getText().toString().toLowerCase()) == 1)
                                        match.add(message.get(i));
                                }
                            }
                        }
                    }
                    else if (p == 1) {
                            if (s == 1) {
                                for (int i = 0; i < message.size(); i++) {
                                    if (checkDate(message.get(i).getDate1())
                                            && kmp.search(message.get(i).getbody().toLowerCase(), pattern.getText().toString().toLowerCase()) == 1)
                                            match.add(message.get(i));
                                }
                            }
                            else if(s==0) {
                                for (int i = 0; i < message.size(); i++) {
                                    if (checkDateBefore(message.get(i).getDate1())
                                            && kmp.search(message.get(i).getbody().toLowerCase(), pattern.getText().toString().toLowerCase()) == 1)
                                        match.add(message.get(i));
                                }
                            }
                            else{
                                for (int i = 0; i < message.size(); i++)
                                    if (kmp.search(message.get(i).getbody().toLowerCase(), pattern.getText().toString().toLowerCase()) == 1)
                                        match.add(message.get(i));
                            }

                        }
                    else {
                        if (s == 1) {
                            for (int i = 0; i < message.size(); i++) {
                                if (checkDate(message.get(i).getDate1()))
                                        match.add(message.get(i));
                            }
                        }
                        else{
                            for (int i = 0; i < message.size(); i++) {
                                if (checkDateBefore(message.get(i).getDate1()))
                                    match.add(message.get(i));
                            }
                        }
                    }


                    long tEnd = System.currentTimeMillis();
                    long tDelta = tEnd - tStart;
                    double elapsedSeconds = tDelta / 1000.0;
                    time.setText("Elaspsed Time : " + elapsedSeconds + "s");
                    adapter = new CustomAdapter(getContext(), match,name.getText().toString(),pattern.getText().toString(),null);
                    time.append("\nMatch Found : "+adapter.getCount());
                    list.setAdapter(adapter);
                }
                else {
                    adapter = new CustomAdapter(getContext(), message);
                    list.setAdapter(adapter);
                }
                ad.cancel();
            }
        });

        AlertDialog.Builder adb=new AlertDialog.Builder(ct);
        adb.setView(v1);
        adb.setTitle("Search");
        ad=adb.create();

        search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                name.setText("");
                pattern.setText("");
                start.setText("");
                end.setText("");
                ad.show();
            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(!isVisibleToUser) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }

    }


    boolean checkDate(Date date){
        if((date.after(d) || date.equals(d)) && (date.before(d1) || date.equals(d1)))
            return true;
        return false;
    }

    boolean checkDateBefore(Date date){
        if(date.before(d1) || date.equals(d1))
            return true;
        return false;
    }


}
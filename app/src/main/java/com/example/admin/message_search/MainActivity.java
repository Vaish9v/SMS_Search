package com.example.admin.message_search;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String id;
    String address;
    String body;
    String date;
    static ArrayList<Message> message;
    static ArrayList<String> contact;
    ListView list;
    ProgressBar pbar;
    static CustomAdapter adapter;
    static ContentResolver cr;
    static int count=0;
    static int sent=0,recieved=0;
    EditText et;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        flag=false;
        message = new ArrayList<>();
        cr=getContentResolver();
        requestPermissionAndContinue();
        new load().execute();
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private class load extends AsyncTask{

        @Override
        protected void onPreExecute() {
            setContentView(R.layout.splashscreen);
            count=0;
           while (true){
                if(flag==true)
                    break;
                requestPermissionAndContinue();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            contact = new ArrayList<>();
            pbar=findViewById(R.id.pbar);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                pbar.setProgress(1,true);
            }
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Uri sms = Uri.parse("content://sms/");
            Cursor c = cr.query( sms, null, null, null, null);
            if (c.moveToFirst())
                do {
                    if (c.getInt(c.getColumnIndex("type")) == 1) {
                        recieved++;
                        id = c.getString(c.getColumnIndex("_id"));
                        address = getNames(c.getString(c.getColumnIndex("address")));
                        body = c.getString(c.getColumnIndex("body"));
                        date = c.getString(c.getColumnIndex("date"));
                        message.add(new Message(id, address, body, date, "Recieved"));
                        if (!contact.contains(address))
                            contact.add(address);
                    } else {
                        sent++;
                        id = c.getString(c.getColumnIndex("_id"));
                        address = getNames(c.getString(c.getColumnIndex("address")));
                        body = c.getString(c.getColumnIndex("body"));
                        date = c.getString(c.getColumnIndex("date"));
                        message.add(new Message(id, address, body, date, "Sent"));
                        if (!contact.contains(address))
                            contact.add(address);
                    }
                } while (c.moveToNext());

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            setContentView(R.layout.activity_main);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.addTab(tabLayout.newTab().setText("Search"));
            tabLayout.addTab(tabLayout.newTab().setText("Stats"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    String getNames(String address){
        Uri uri;
        if(address.matches("^[+0-9]+") && address.length()>9){
            if (address.contains("+"))
                uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address.substring(2)));
            else
                uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
            String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
            Cursor c1 = cr.query( uri, projection, null, null, null);
            if (c1 != null) {
                if(c1.moveToFirst())
                    return c1.getString(0);
            }
        }
        return address;

    }

    private void requestPermissionAndContinue(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS}, 0);

        }else{
            flag=true;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if(grantResults.length>0 && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS)== PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            flag=true;
        }
        else if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS)== PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_DENIED){
           if(count>5)
               onDestroy();
           count++;
           SystemClock.sleep(1000);
        }
    }
}

package com.example.admin.message_search;




import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Message{

    String id;
    String address;
    String body;
    String date;
    String type;

    public Message(String id,String address,String body,String date,String type){
        this.id = id;
        this.body =body;
        this.address=address;
        this.date=date;
        this.type=type;
    }

    public String getid() {
        return id;
    }

    public String getbody() {
        return body;
    }

    public String getaddress() {
        return address;
    }

    public String gettype() {
        return type;
    }

    public String getDate() {
        long datetime = Long.parseLong(date);
        DateFormat formatter = new SimpleDateFormat("dd/MM/YY");
        Date date = new Date(datetime);
        return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
    }

    public Date getDate1() {
        long datetime = Long.parseLong(date);
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(datetime);
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        Date temp = new Date(datetime);
        Date date = null;
        try {
            date = sdf.parse(DateFormat.getDateInstance(DateFormat.SHORT).format(temp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
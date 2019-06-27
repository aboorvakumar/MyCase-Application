package com.compunet.mycase;

public class DataModel {


    String client_name,case_name,case_id,case_date,case_time;



    public String getcase_id() {
        return case_id;
    }

    public void setcase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getcase_name() {
        return case_name;
    }

    public void setcase_name(String case_name) {
        this.case_name = case_name;
    }
    public String getclient_name() {
        return client_name;
    }

    public void setclient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getcase_date() {
        return case_date;
    }

    public void setcase_date(String case_date) {
        this.case_date = case_date;
    }

    public String getcase_time() {
        return case_time;
    }

    public void setcase_time(String case_time) {
        this.case_time = case_time;
    }


}
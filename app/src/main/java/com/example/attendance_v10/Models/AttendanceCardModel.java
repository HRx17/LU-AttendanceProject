package com.example.attendance_v10.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttendanceCardModel {
   String Day;
   ArrayList<String> names;

    public AttendanceCardModel() {
    }

    public AttendanceCardModel(String day, ArrayList<String> names) {
        Day = day;
        this.names = names;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
}

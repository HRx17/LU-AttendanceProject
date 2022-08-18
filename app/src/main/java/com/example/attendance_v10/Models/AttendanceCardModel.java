package com.example.attendance_v10.Models;

import java.util.List;
import java.util.Map;

public class AttendanceCardModel {
   String Day;
   List<String> names;

    public AttendanceCardModel() {
    }

    public AttendanceCardModel(String day, List<String> names) {
        Day = day;
        this.names = names;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}

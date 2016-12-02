package com.example.nicole.nicoleferreirasilverio_pset5;

/**
 * Created by Nicole on 2-12-2016.
 */

public class TodoItem {

    private String title;
    private String completed;

    private TodoItem(String title, String completed){
        this.title = title;
        this.completed = completed;
    }

    public String getTitle(){
        return title;
    }

    public String getCompleted(){
        return completed;
    }
}

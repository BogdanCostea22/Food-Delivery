package com.sd.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Cache {

    private List<Order> list = null;
    private long time = 0;

    public void setList(List<Order> list){
        this.list = new ArrayList<>();
        this.list.addAll(list);
    }

    public List<Order> getList(){
        return list;
    }

    public void setTime(long time){
        this.time = time;
    }

    public long getTime(){
        return time;
    }
}

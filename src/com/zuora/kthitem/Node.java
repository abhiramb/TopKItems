package com.zuora.kthitem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek.battepati on 5/31/16.
 */
public class Node {
    String user;
    String  page;
    List<String> userHistory;
    Node prev;
    Node next;

    public Node(String user, String page){
        this.user = user;
        this.page = page;
        if(userHistory == null) {
            userHistory = new ArrayList<String>();
        }
        userHistory.add(page);
    }

}



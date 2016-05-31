package com.zuora.kthitem;

import com.zuora.kthitem.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by abhishek.battepati on 5/31/16.
 */
public class QueuedFrequentItem {
    int capacity;
    HashMap<String, Node> map = new HashMap<String, Node>();
    HashMap<String,Integer> multiple_hits = new HashMap<String, Integer>();
    Node head = null;
    Node end = null;

    public QueuedFrequentItem(int capacity) {
        this.capacity = capacity;
    }



    public void remove(Node n){
        if(n.prev!=null){
            n.prev.next = n.next;
        }else{
            head = n.next;
        }

        if(n.next!=null){
            n.next.prev = n.prev;
        }else{
            end = n.prev;
        }

    }

    public void setHead(Node n){
        n.next = head;
        n.prev = null;

        if(head!=null)
            head.prev = n;

        head = n;

        if(end ==null)
            end = head;
    }

    public void set(String user, String page) {
        if(map.containsKey(user)){
            Node old = map.get(user);
            old.userHistory.add(page);
            if(old.userHistory.size() == 3){
                String csv = String.join(",", old.userHistory);
                if(multiple_hits.containsKey(csv)){
                    int k = multiple_hits.get(csv);
                    multiple_hits.put(csv, k + 1);
                } else {
                    multiple_hits.put(csv, 1);
                }

                old.userHistory.remove(0);
            }
            remove(old);
            setHead(old);
        }else{
            Node created = new Node(user, page);
            if(map.size()>=capacity){
                map.remove(end.user);
                remove(end);
                setHead(created);

            }else{
                setHead(created);
            }

            map.put(user, created);
        }
    }

    public void SetupQueue() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(QueuedFrequentItem.class.getResourceAsStream("weblog.txt")));
        String line = null;
        while ((line = reader.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, " ");
            String user = st.nextToken();
            String page = st.nextToken();
            this.set(user,page);
        }

    }

    private void PrintTopN() {
        int maxValue = Collections.max(multiple_hits.values());

        for(Map.Entry<String, Integer> entry : multiple_hits.entrySet()) {
            if(entry.getValue() == maxValue) {
                System.out.println(entry.getKey());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        QueuedFrequentItem qfi = new QueuedFrequentItem(1000);
        qfi.SetupQueue();
        qfi.PrintTopN();
    }

}


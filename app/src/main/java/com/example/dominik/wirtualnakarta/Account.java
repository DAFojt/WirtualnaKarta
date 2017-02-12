package com.example.dominik.wirtualnakarta;

import java.io.Serializable;

/**
 * Created by Dominik on 20.11.2016.
 */
public class Account implements Serializable{
    private String acc_number;
    private int acc_balance;
    private String owner_name;

    Account(String number, int balance, String name)
    {
        add(number, balance, name);
    }

    public int add(String id, int balance, String name)
    {
        acc_number = id;
        acc_balance = balance;
        owner_name = name;


        return 1;
    }

    public int delete_acc()
    {
        return 1;
    }

    public void enrich(int money)
    {
        acc_balance += money;
    }

    public void unenrich(int money)
    {
        acc_balance -= money;
    }

    public int getBalance()
    {
        return this.acc_balance;
    }

    public String getNumber(){return this.acc_number;}

    public String getOwner(){return this.owner_name;}

    public void setBalance(int balance){this.acc_balance = balance;}

    public void setNumber(String number){this.acc_number = number;}

    public void setOwner(String owner){this.owner_name = owner;}



}

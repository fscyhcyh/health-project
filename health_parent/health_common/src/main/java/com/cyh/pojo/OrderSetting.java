package com.cyh.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderSetting implements Serializable {
    private Integer id;
    private Date orderDate;
    private int number;
    private int reservations;

    @Override
    public String toString() {
        return "OrderSetting{" +
                "orderDate=" + orderDate +
                ", number=" + number +
                ", reservations=" + reservations +
                '}';
    }

    public OrderSetting() {
    }


    public OrderSetting(int id, Date orderDate, int number, int reservations) {
        this.id = id;
        this.orderDate = orderDate;
        this.number = number;
        this.reservations = reservations;
    }

    public OrderSetting(Date orderDate, int number) {
        this.orderDate = orderDate;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getReservations() {
        return reservations;
    }

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }
}

package com.java.travel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RouteInfo{

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    
    private String routeStart;
    private String routeEnd;
    private Date date;
    private String busNumber;
    private String driverLastName;
    private Integer busSeatCount;
    private Double ticketPrice;
    private Integer ticketsCount;

    public RouteInfo() {
        this.routeStart = "Plovdiv";
    }
    
    public String getRouteStart() {
        return routeStart;
    }

    public String getRouteEnd() {
        return routeEnd;
    }

    public void setRouteEnd(String routeEnd) {
        this.routeEnd = routeEnd;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public Integer getBusSeatCount() {
        return busSeatCount;
    }

    public void setBusSeatCount(Integer busSeatCount) {
        this.busSeatCount = busSeatCount;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getTicketsCount() {
        return ticketsCount;
    }

    public void setTicketsCount(Integer ticketsCount) {
        this.ticketsCount = ticketsCount;
    }

    public Double getTotal() {
        return this.getTicketsCount()*this.getTicketPrice();
    }

    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s", this.getRouteStart() + "-" + this.getRouteEnd()));
        sb.append(String.format("%-20s", dateFormat.format(this.getDate())));
        sb.append(String.format("%-10s", this.getBusNumber()));
        sb.append(String.format("%-20s", this.getDriverLastName()));
        sb.append(String.format("%-4s", this.getBusSeatCount()));
        sb.append(String.format("%-4s", this.getTicketsCount()));
        sb.append(String.format("%-6s", this.getTicketPrice() + " lv."));
        sb.append(String.format("%-8s", this.getTotal() + " lv."));

        return sb.toString();
    }

    public String toCSV() {
        return this.getRouteEnd() + "," + dateFormat.format(this.getDate()) 
                + "," + this.getBusNumber() + "," + this.getDriverLastName()
                + "," + this.getBusSeatCount() + "," + this.getTicketsCount() 
                + "," + this.getTicketPrice();
    }

 
}

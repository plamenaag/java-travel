package com.java.travel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RouteInfoUtils {
        
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    
    public static RouteInfo inputRouteInfo(){
        Boolean inputIsIncorrect = false;
        RouteInfo routeInfo = new RouteInfo();
        System.out.println("Input route info: ");
        System.out.println("(Route,Date,Registration number,Driver,Seat count,Price)");
        String rawRouteInfoStr = ScannerFactory.getInstance().nextLine();
        String[] routeInfoParts = rawRouteInfoStr.split(",");
        if (routeInfoParts.length == 6) {
            try {
                routeInfo.setRouteEnd(routeInfoParts[0]);
                routeInfo.setDate(dateFormat.parse(routeInfoParts[1]));
                routeInfo.setBusNumber(routeInfoParts[2]);
                routeInfo.setDriverLastName(routeInfoParts[3]);
                routeInfo.setBusSeatCount(Integer.parseInt(routeInfoParts[4]));
                routeInfo.setTicketPrice(Double.parseDouble(routeInfoParts[5]));
            } catch (ParseException ex) {
                inputIsIncorrect = true;
            }
        } else {
            inputIsIncorrect = true;
        }

        if (inputIsIncorrect) {
            System.out.println("Wrong input! Do you want to try again (y/n):");
            if (ScannerFactory.getInstance().nextLine().equals("y")) {
                return inputRouteInfo();
            } else {
                return null;
            }
        }

        Random random = new Random();
        int soldTickets = random.nextInt(routeInfo.getBusSeatCount() + 1);
        routeInfo.setTicketsCount(soldTickets);

        return routeInfo;
    }

    public static List<RouteInfo> sortByTotal(List<RouteInfo> list, Boolean dsc) {
        Collections.sort(list, new Comparator<RouteInfo>() {
            @Override
            public int compare(RouteInfo o1, RouteInfo o2) {
                if (dsc) {
                    return o2.getTotal().compareTo(o1.getTotal());
                } else {
                    return o1.getTotal().compareTo(o2.getTotal());
                }
            }
        });
        
        return list;
    }

    public static List<RouteInfo> filterByPeriod(List<RouteInfo> list, Date fromDate, Date toDate) {
        List<RouteInfo> filteredList = new ArrayList<>();
        for (RouteInfo routeInfo : list) {
            if (routeInfo.getDate().after(fromDate) && routeInfo.getDate().before(toDate)) {
                filteredList.add(routeInfo);
            }
        }
        return filteredList;
    }

    public static List<RouteInfo> filterByRouteEnd(List<RouteInfo> list, String routeEnd) {
        List<RouteInfo> filteredList = new ArrayList<>();
        for (RouteInfo routeInfo : list) {
            if (routeInfo.getRouteEnd().equalsIgnoreCase(routeEnd)) {
                filteredList.add(routeInfo);
            }
        }
        return filteredList;
    }
    
    
    private static HashMap<String,Double> getRoutesTotals(List<RouteInfo> routeInfos){
        HashMap<String, Double> map = new HashMap<>();
        for (RouteInfo routeInfo : routeInfos) {
            if (map.containsKey(routeInfo.getRouteEnd())) {
                Double routeTotal = map.get(routeInfo.getRouteEnd());
                map.put(routeInfo.getRouteEnd(), routeTotal + routeInfo.getTotal());
            } else {
                map.put(routeInfo.getRouteEnd(), routeInfo.getTotal());
            }
        }
        return map;
    } 

    public static String mostProfitableRoute(List<RouteInfo> routeInfos) {
       HashMap<String, Double> map = getRoutesTotals(routeInfos);

        String route = null;
        Double total = 0.0;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (route == null || total < entry.getValue()) {
                route = entry.getKey();
                total = entry.getValue();
            }
        }
        return route + ": " + total;
    }
    
    public static String leastProfitableRoute(List<RouteInfo> routeInfos) {
        HashMap<String, Double> map = getRoutesTotals(routeInfos);

        String route = null;
        Double total = 0.0;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (route == null || total > entry.getValue()) {
                route = entry.getKey();
                total = entry.getValue();
            }
        }
        return route + ": " + total;
    }

    public static void printAll(List<RouteInfo> list) {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s", "Route"));
        sb.append(String.format("%-20s", "Date"));
        sb.append(String.format("%-10s", "Registration number"));
        sb.append(String.format("%-20s", "Driver"));
        sb.append(String.format("%-4s", "Seat count"));
        sb.append(String.format("%-4s", "Sold tickets"));
        sb.append(String.format("%-6s", "Price per ticket"));
        sb.append(String.format("%-8s", "Total"));

        System.out.println(sb.toString());

        for (RouteInfo routeInfo : list) {
            System.out.println(routeInfo.toString());
        }
    }

    public static List<RouteInfo> deserializeRouteInfos(List<String> rawRouteInfos){
        List<RouteInfo> routeInfos = new ArrayList<>();
        for (String str : rawRouteInfos) {
            RouteInfo routeInfo = deserializeRouteInfo(str);
            if(routeInfo!=null){
                routeInfos.add(routeInfo);
            }
        }
        return routeInfos;
    }

    public static RouteInfo deserializeRouteInfo(String rawRouteInfo) {
        
        String[] routeInfoParts = rawRouteInfo.split(",");
        RouteInfo routeInfo = new RouteInfo();
        try{
            routeInfo.setRouteEnd(routeInfoParts[0]);
            routeInfo.setDate(dateFormat.parse(routeInfoParts[1]));
            routeInfo.setBusNumber(routeInfoParts[2]);
            routeInfo.setDriverLastName(routeInfoParts[3]);
            routeInfo.setBusSeatCount(Integer.parseInt(routeInfoParts[4]));
            routeInfo.setTicketsCount(Integer.parseInt(routeInfoParts[5]));
            routeInfo.setTicketPrice(Double.parseDouble(routeInfoParts[6]));
        }catch(ParseException ex){
            routeInfo = null;
        }
        return routeInfo;
    }

    public static String serializeRouteInfos(List<RouteInfo> routeInfos) {
        StringBuilder sb = new StringBuilder();
        for (RouteInfo routeInfo : routeInfos) {
            sb.append(routeInfo.toCSV());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
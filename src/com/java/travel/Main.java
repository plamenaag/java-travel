package com.java.travel;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Main {

    private static List<RouteInfo> routeInfos;
    private static final String FILE_NAME = ".\\myFile.txt";

    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
        //Call FileUtils.readFile to read the file
        List<String> rawRouteInfos = FileUtils.readFile(FILE_NAME);

        if(rawRouteInfos!=null && rawRouteInfos.size()>0){
            //Call RouteInfoUtils.deserializeRouteInfos to deserialize the content of the file
            routeInfos = RouteInfoUtils.deserializeRouteInfos(rawRouteInfos);
        }
        
        runMenu();

        ScannerFactory.closeInstance();
        //deserialize routeInfos
        String serializedRouteInfos = RouteInfoUtils.serializeRouteInfos(routeInfos);
        //then write to file
        try {
            FileUtils.writeFile(serializedRouteInfos, FILE_NAME);
        } catch (IOException ex) {
            System.out.println("Saving data to file failed! :( ");
        }

        System.out.println("Closing program...");
    }

    private static void runMenu() {
        Integer input = 0;

        do {
            //Print the menu
            MenuUtils.printMenu();
            //Get user input
            input = MenuUtils.selectMenuOption();
            if (input == null) {
                return;
            }
            //Check input
            switch (input) {
                case 1:
                    inputRoute();
                    break;
                case 2:
                    filterByPeriod();
                    break;
                case 3:
                    printAllRoutes();
                    break;
                case 4:
                    filterByPeriodAndSave();
                    break;
                case 5:
                    showMostProfitable();
                    break;
                case 6:
                    showLeastProfitable();
                    break;
                case 7:
                    filterByRouteEnd();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Please select option from 1 to 8!");
            }
        } while (input != 8);
    }

    private static void inputRoute() {
        String inputAgain = "";
        do {
            //Input new route info
            RouteInfo routeInfo = RouteInfoUtils.inputRouteInfo();
            //check if is not null
            if (routeInfo != null) {
                // add it to the list with routeInfos
                routeInfos.add(routeInfo);
            }
            System.out.println("Do you want to add another route info (y/n)?");
            inputAgain = ScannerFactory.getInstance().nextLine();
        } while (inputAgain.equals("y"));
    }

    private static void filterByPeriod() {
        String inputAgain = "";
        do {
            Date fromDate = MenuUtils.getDate("Input from date: ");
            if (fromDate == null) {
                System.out.println("Wrong date input! Returning to menu...");
                return;
            }
            Date toDate = MenuUtils.getDate("Input to date: ");
            if (toDate == null) {
                System.out.println("Wrong date input! Returning to menu...");
                return;
            }

            //filter the routeInfos by the dates
            List<RouteInfo> list = RouteInfoUtils.filterByPeriod(routeInfos, fromDate, toDate);
            list = RouteInfoUtils.sortByTotal(list, true);
            RouteInfoUtils.printAll(list);
            System.out.println("Do you want to filter by period again(y/n)?");
            inputAgain = ScannerFactory.getInstance().nextLine();
        } while (inputAgain.equals("y"));
    }

    private static void printAllRoutes(){
         List<RouteInfo> list = RouteInfoUtils.sortByTotal(routeInfos, true);
         RouteInfoUtils.printAll(list);
    }
    
    private static void filterByPeriodAndSave() {
        Date fromDate = MenuUtils.getDate("Input from date: ");
        if (fromDate == null) {
            System.out.println("Wrong date input! Returning to menu...");
            return;
        }
        Date toDate = MenuUtils.getDate("Input to date: ");
        if (toDate == null) {
            System.out.println("Wrong date input! Returning to menu...");
            return;
        }

        //filter the routeInfos by the dates
        List<RouteInfo> list = RouteInfoUtils.filterByPeriod(routeInfos, fromDate, toDate);
        list = RouteInfoUtils.sortByTotal(list, false);
        StringBuilder sb = new StringBuilder();
        for (RouteInfo routeInfo : list) {
            sb.append(routeInfo.toString());
            sb.append(System.lineSeparator());
        }

        System.out.println("Choose file name: ");
        String fileName = ScannerFactory.getInstance().nextLine();
        try {
            FileUtils.writeFile(sb.toString(), ".\\" + fileName + ".txt");
        } catch (IOException ex) {
            System.out.println("Saving file failed!");
        }

    }

    private static void showMostProfitable() {
        Date fromDate = MenuUtils.getDate("Input from date: ");
        if (fromDate == null) {
            System.out.println("Wrong date input! Returning to menu...");
            return;
        }
        Date toDate = MenuUtils.getDate("Input to date: ");
        if (toDate == null) {
            System.out.println("Wrong date input! Returning to menu...");
            return;
        }

        //filter the routeInfos by the dates
        List<RouteInfo> list = RouteInfoUtils.filterByPeriod(routeInfos, fromDate, toDate);
        System.out.println("Most profitable route:");
        System.out.println(RouteInfoUtils.mostProfitableRoute(list));
    }

    private static void showLeastProfitable() {
        Date fromDate = MenuUtils.getDate("Input from date: ");
        if (fromDate == null) {
            System.out.println("Wrong date input! Returning to menu...");
            return;
        }
        Date toDate = MenuUtils.getDate("Input to date: ");
        if (toDate == null) {
            System.out.println("Wrong date input! Returning to menu...");
            return;
        }

        //filter the routeInfos by the dates
        List<RouteInfo> list = RouteInfoUtils.filterByPeriod(routeInfos, fromDate, toDate);
        System.out.println("Least profitable route:");
        System.out.println(RouteInfoUtils.leastProfitableRoute(list));
    }

    private static void filterByRouteEnd() {
        String routeEnd = MenuUtils.getRouteEnd();
        List<RouteInfo> list = RouteInfoUtils.filterByRouteEnd(routeInfos, routeEnd);
        RouteInfoUtils.printAll(list);
    }

}

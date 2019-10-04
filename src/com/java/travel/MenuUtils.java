package com.java.travel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuUtils {

    public static void printMenu() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Please select option: \n");
        stringBuilder.append("1. Input route info \n");
        stringBuilder.append("2. Filter by period \n");
        stringBuilder.append("3. Show all routes \n");
        stringBuilder.append("4. Filter by period and save to file \n");
        stringBuilder.append("5. Most profitable route for period \n");
        stringBuilder.append("6. Least profitable route for period \n");
        stringBuilder.append("7. Filter by route end \n");
        stringBuilder.append("8. Exit");
        System.out.println(stringBuilder);
    }

    public static Integer selectMenuOption() {
        System.out.print("Choose option: ");
        Integer input = null;
        try {
            input = Integer.parseInt(ScannerFactory.getInstance().nextLine());
        } catch (Exception ex) {
            System.out.println("Wrong option input! Do you want to try again(y/n)?");
            if (ScannerFactory.getInstance().nextLine().equalsIgnoreCase("y")) {
                return selectMenuOption();
            } else {
                return null;
            }
        }
        return input;
    }

    public static Date getDate(String msg) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
      
        System.out.print(msg);
        String strDate = ScannerFactory.getInstance().nextLine();
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException ex) {
            System.out.println("Wrong date input! Do you want to try again(y/n)?");
            if (ScannerFactory.getInstance().nextLine().equalsIgnoreCase("y")) {
                return getDate(msg);
            } else {
                return null;
            }
        }
        return date;
    }

    public static String getRouteEnd() {
        System.out.print("Input route end: ");
        String routeEnd = ScannerFactory.getInstance().nextLine();
        return routeEnd;
    }

}

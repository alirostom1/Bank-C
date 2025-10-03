package io.github.alirostom1.bankc.ui;

import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientMenu clientMenu;
    private final TransactionMenu transactionMenu;
    private final AnalyticsMenu analyticsMenu;

    public MainMenu(ClientMenu clientMenu,TransactionMenu transactionMenu,AnalyticsMenu analyticsMenu){
        this.clientMenu = clientMenu;
        this.transactionMenu = transactionMenu;
        this.analyticsMenu = analyticsMenu;
    }

    public final void run(){
        while(true){
            try{
                System.out.println("********MainMenu********");
                System.out.println("1. Client menu");
                System.out.println("2. Transaction menu");
                System.out.println("3. Analytics menu");
                System.out.println("4. Exit ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        clientMenu.run();
                        break;
                    case "2":
                        transactionMenu.run();
                        break;
                    case "3":
                        analyticsMenu.run();
                        break;
                    case "4":
                        return;
                    default:
                        break;
                }
            }catch(Exception e){
            }
        }
    }
}

package com.dsad.browser;

import java.util.Scanner;

import com.dsad.browser.cli.CLIApp;

public class Main {
    
    /**
     * Main driver code
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(":::::::::::::::::::::::::MUSIC PLAYLIST MANAGER:::::::::::::::::::::::::\n\n");

        System.out.println("Which one do you want to use?");
        System.out.println("\t1. Command Line Interface");
        System.out.println("\t2. Graphical User Interface");
        System.out.println("\t0. Exit");

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1: CLIApp app = new CLIApp(sc);
                app.start();
                break;
            case 2: // GUI
                break;
            case 0: System.out.println("Exiting....");
                break;
            default:
                break;
        }

        sc.close();
    }
}

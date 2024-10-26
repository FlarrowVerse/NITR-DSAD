package com.dsad.browser.cli;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import com.dsad.browser.core.Page;
import com.dsad.browser.core.BrowserHistory;

public class CLIApp {

    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private Scanner sc; // for input
    private BrowserHistory manager;

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    /**
     * Parameterized constructor
     * @param sc
     */
    public CLIApp(Scanner sc) {
        this.sc = sc;
        this.manager = new BrowserHistory(); // no history loaded
    }

    /**
     * Saves current history
     */
    private void saveHistory() {
        if (this.manager == null) System.out.println("No history to save!");
        else {
            System.out.print("Do you want to save the last current history?(Y/N)");
            if ("yY".indexOf(this.sc.nextLine().charAt(0)) != -1) {
                BrowserHistory.saveHistory(this.manager);
                System.out.printf("Saved history %s....\n", this.manager.getDate());
            }
        }
    }

    /**
     * Edit songs of the current playlist
     */
    private void loadedHistory() {

        boolean back = false;

        if (!BrowserHistory.isLoaded()) {
            System.out.print("Enter the date you want to view the history for(yyyy-MM-dd): ");
            BrowserHistory.loadHistory(sc.nextLine());
        }

        while (BrowserHistory.isLoaded() && !back) {
            
            System.out.println("--------------------------------");
            System.out.println("LOADED HISTORY: " + ((BrowserHistory.isLoaded())? BrowserHistory.getLoadedHistory(): "")); // if already loaded
            System.out.println("--------------------------------");
            System.out.println("\t1. Load History");
            System.out.println("\t2. Search History");
            System.out.println("\t3. Load Page");
            System.out.println("\t4. Remove Page"); 
            System.out.println("\t5. Save History\n");
            System.out.println("\t0. Go to Main Menu");
    
            System.out.print("Enter your choice: ");
            int option = this.sc.nextInt(); this.sc.nextLine();
    
            String title = "", url = "", line = ""; // strings that might be needed
            long position = -1;
    
            switch (option) {
                case 1: System.out.print("Enter the date you want to view the history for(yyyy-MM-dd): ");
                    BrowserHistory.loadHistory(sc.nextLine());
                    break;
                case 2: System.out.print("Enter page title ('' default): ");
                    title = sc.nextLine();
                    System.out.print("Enter page URL ('' defaullt): ");
                    url = sc.nextLine();
                    BrowserHistory.searchLoadedHistory(title, url, position);
                    break;
                case 3: System.out.print("Enter page title ('' default): ");
                    title = sc.nextLine();
                    System.out.print("Enter page URL ('' defaullt): ");
                    url = sc.nextLine();
                    System.out.print("Enter page position (-1 defaullt): ");
                    line = sc.nextLine(); position = (line.isEmpty())? -1: Long.parseLong(line);

                    if (BrowserHistory.searchLoadedHistory(title, url, position) == null) {
                        System.out.println("Could not load the page you requested!");
                    } else {
                        Page oldPage = BrowserHistory.searchLoadedHistory(title, url, position);
                        this.manager.visitNewPage(oldPage.getURL().toString(), oldPage.getTitle(), oldPage.getTags(),
                                oldPage.isBookmarked());
                    }
                    break;
                case 4: 
                    System.out.print("Enter page title ('' default): ");
                    title = sc.nextLine();
                    System.out.print("Enter page URL ('' defaullt): ");
                    url = sc.nextLine();
                    System.out.print("Enter page position (-1 defaullt): ");
                    line = sc.nextLine(); position = (line.isEmpty())? -1: Long.parseLong(line);

                    BrowserHistory.removeLoadedHistoryPage(title, url, position);
                    break;
                case 5: BrowserHistory.saveLoadedHistory();
                case 0: 
                default: back = true;
                    break;
            }
        }
    }

    /**
     * Controls the display and running of the menu options
     * @return
     */
    public boolean mainMenu() {
        System.out.println("\n-------------------\nMAIN MENU\n-------------------\n");
        System.out.print("Current Page: ");
        if (this.manager == null) {
            System.out.println("<---NEW TAB--->.");
        } else {
            System.out.println(this.manager.getCurrentPage());
        }

        System.out.println("\t1. Visit new Page"); // done
        System.out.println("\t2. Go Back"); // done
        System.out.println("\t3. Go Forward"); // done
        System.out.println("\t4. Undo Navigation"); // done
        System.out.println("\t5. Redo Navigation"); // done
        System.out.println("\t6. View History"); // done
        System.out.println("\t7. Search History"); // done
        System.out.println("\t8. Load Page"); // done
        System.out.println("\t9. Load Bookmarked Page"); // done
        System.out.println("\t10. Remove Page"); // done
        System.out.println("\t11. Clear History"); // done
        System.out.println("\t12. Show current Bookmarks"); // done 
        System.out.println("\t13. Bookmark Current Page"); // done 
        System.out.println("\t14. Load Saved History"); // done
        System.out.println("\t15. Save Currrent History\n"); // done
        System.out.println("\t0. Exit");

        System.out.print("Enter your choice: ");
        int option = this.sc.nextInt(); this.sc.nextLine();

        String url = "", title = "", line = ""; // strings that might be needed
        long index = -1;
        List<String> tags = new ArrayList<>();

        switch (option) {
            case 1: System.out.print("Enter the page URL: "); url = sc.nextLine(); // getting the url
                System.out.print("Enter the page title: "); title = sc.nextLine(); // getting the title

                // all the tags
                System.out.print("Enter the tags associated (space separated): "); 
                tags.addAll(Arrays.asList(sc.nextLine().split(" ")));

                this.manager.visitNewPage(url, title, tags, false);
                break;
            case 2: this.manager.goBack(); // go a page back in history
                break;
            case 3: this.manager.goForward(); // go a page forward in the history
                break;
            case 4: this.manager.undo(); // undo the last navigation action
                break;
            case 5: this.manager.redo();
                break;
            case 6: System.out.println(this.manager); // showing the history
                break;
            case 7: System.out.print("Do you want to search by title? (Y/N): ");
                if ("yY".indexOf(sc.nextLine()) != -1) { // if yes
                    System.out.print("Enter the title ('' default): ");
                    title = sc.nextLine();
                } else {
                    System.out.print("Enter the url ('' default): ");
                    url = sc.nextLine();
                }

                this.manager.searchHistory(title, url);
                break;
            case 8: System.out.print("Enter the title ('' default): ");
                title = sc.nextLine();
                System.out.print("Enter the URL ('' default): ");
                url = sc.nextLine();
                System.out.print("Enter the index(-1 default): ");
                line = sc.nextLine(); index = (line.isEmpty())? -1: Long.parseLong(line);

                this.manager.loadPage(title, url, index);
                break;
            case 9: System.out.print("Enter the bookmark title ('' default): ");
                title = sc.nextLine();
                System.out.print("Enter the bookmark URL ('' default): ");
                url = sc.nextLine();
                this.manager.loadBookmarkedPage(title, url);
                break;
            case 10: System.out.print("Enter the title ('' default): ");
                title = sc.nextLine();
                System.out.print("Enter the URL ('' default): ");
                url = sc.nextLine();
                System.out.print("Enter the index (-1 default): ");
                line = sc.nextLine(); index = (line.isEmpty())? -1: Long.parseLong(line);

                this.manager.removePage(title, url, index);
                break;
            case 11: System.out.print("Do you really want to clear the history? (Y/N): ");
                if ("yY".indexOf(sc.nextLine()) != -1) { // if yes
                    this.manager.clearHistory();
                }
                break;
            case 12: this.manager.showBookmarks();
                break;
            case 13: this.manager.bookmarkCurrentPage();
                break;
            case 14: this.loadedHistory(); // go to the loaded history menu
                break;
            case 15: saveHistory();
                break;
            case 0:
            default: System.err.println("Exiting....");
                return false;
        }

        return true;
    }

    /**
     * Entry point for CLI app
     */
    public void start() {
        boolean run = true;
        System.out.println("\n\n=================================BROWSER HISTORY MANAGER - COMMAND LINE INTERFACE=================================\n\n");
        while (run) {
            run = mainMenu();
        }
    }
}

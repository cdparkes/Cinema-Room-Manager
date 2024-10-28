package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    protected static int currentIncome = 0;


    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Write your code here
        menu();
    }

    public static void menu() {
        boolean repeat = true;
        // Create new cinema object to later keep track of current income
        Cinema cinema = new Cinema();

        System.out.println("Enter the number of rows:");
        int totalRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int totalSeatsPerRow = scanner.nextInt();

        String[][] cinemaArr = new String[totalRows][totalSeatsPerRow];
        initialiseArray(cinemaArr);
        System.out.println();

        while (repeat) {
            System.out.println("""
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit""");
            int input = scanner.nextInt();
            System.out.println();

            switch (input) {
                case 1 -> printCinema(cinemaArr);
                case 2 -> {
                    getBookSeatInput(cinemaArr, totalRows, totalSeatsPerRow);
                }
                case 3 -> getStatistics(cinemaArr, totalRows, totalSeatsPerRow);
                case 0 -> {
                    repeat = false;
                }
            }
        }
    }

    private static void getStatistics(String[][] cinema, int totalRows, int totalSeatsPerRow) {
        int bookedCounter = 0, currentIncome = 0, totalIncome = 0;
        double bookedPercentage = 0.0;

        // Calculate the number of purchased seats
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalSeatsPerRow; j++) {
                if (cinema[i][j].equals("B")) {
                    bookedCounter++;
                }
            }
        }
        // Calculate the percentage of purchased seats
        bookedPercentage = ((double) bookedCounter / (totalRows * totalSeatsPerRow)) * 100;


        // Output of statistics
        System.out.printf("\nNumber of purchased tickets: %d\n" +
                "Percentage: %.2f%%\n" +
                "Current income: $%d\n" +
                "Total income: $%d\n\n", bookedCounter, bookedPercentage, Cinema.currentIncome, getTotalIncome(totalRows, totalSeatsPerRow));
    }

    public static String[][] getBookSeatInput(String[][] arr, int totalRows, int totalSeatsPerRow) {
        while (true) {
            // Getting inputs for row and seat number
            System.out.println("Enter a row number:");
            int bookRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int bookSeat = scanner.nextInt();

            // checking for seats out of the available area and checking for availability of the seat
            if (bookRow > totalRows || bookSeat > totalSeatsPerRow) {
                System.out.println("Wrong input");
            } else if (!arr[bookRow - 1][bookSeat - 1].equals("S")) {
                System.out.println("That ticket has already been purchased!");
            } else {
                bookSeat(arr, bookRow, bookSeat);
                // Getting the ticket price and adding it to a running total
                int ticketPrice = calculateTicketPrice(totalRows, totalSeatsPerRow, bookRow);
                Cinema.currentIncome += ticketPrice;
                System.out.println();
                System.out.println("Ticket price: $" + ticketPrice);
                System.out.println();
                return arr;
            }
        }
    }

    private static String[][] bookSeat(String[][] arr, int bookRow, int bookSeat) {
        // simply just setting the seat that is getting booked to 'B'
        arr[bookRow - 1][bookSeat - 1] = "B";
        return arr;
    }

    public static String[][] initialiseArray(String[][] arr) {
        // initializes the Array that is created in the menu method to all contain 'S' to show an empty seat
        for (String[] strings : arr) {
            Arrays.fill(strings, "S");
        }
        return arr;
    }

    public static void printCinema(String[][] arr) {
        // parse through the passed through array and print out all the values
        System.out.println("Cinema:");
        for (int i = 0; i <= arr.length; i++) {
            for (int j = 0; j <= arr[0].length; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("  ");
                } else if (i == 0) {
                    System.out.print(j + " ");
                } else if (j == 0) {
                    System.out.print(i + " ");
                } else {
                    System.out.print(arr[i - 1][j - 1] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int getTotalIncome(int totalRows, int totalSeatsPerRow) {
        int totalIncome = 0;
        // calculates the total income for the entire cinema if the total amount of seats is less than 60
        if (totalRows * totalSeatsPerRow <= 60) {
            totalIncome = totalRows * totalSeatsPerRow * 10;
        } else {
            // calculates the total income when over 60 seats. front half being more expensive than back half
            int frontRows = totalRows / 2;
            int backRows = totalRows - frontRows;

            totalIncome += frontRows * totalSeatsPerRow * 10;
            totalIncome += backRows * totalSeatsPerRow * 8;
        }
        return totalIncome;
    }

    public static int calculateTicketPrice(int totalRows, int totalSeatsPerRow, int bookRow) {
        int ticketPrice = 0;
        // calculate the ticket price of a singular ticket to print out on booking of a ticket
        if (totalRows * totalSeatsPerRow <= 60) {
            ticketPrice = 10;
        } else {
            if (totalRows / 2 >= bookRow) {
                ticketPrice = 10;
            } else {
                ticketPrice = 8;
            }
        }
        return ticketPrice;
    }
}
package Application;

import DataProvider.DataProvider;
import DataProvider.DataReader;

import java.util.Scanner;

class main {

    /**
     * Application.main function which is executed.
     * We then delegate the task further down to the Application.SearchHandler.
     * If the user chooses a top k search, we also ask for the k value.
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        // ascii_art();

        String userChoiceComputationMode = args[2];
        String userChoiceTransducerMode = args[1];
        String userChoiceFileInput = args[0];

        System.out.println(main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        System.out.println(args[0] + " " + args[1] + " " + args[2]);

        DataReader dataReader;
        SearchHandler searchHandler;
        Scanner scanner = new Scanner(System.in);


        if (userChoiceTransducerMode.equals("provided")) {
            dataReader = new DataReader(userChoiceFileInput, false);

        } else if (userChoiceTransducerMode.equals("generate")) {
            dataReader = new DataReader(userChoiceFileInput, true);
        } else { // error ...
            return;
        }

        searchHandler = new SearchHandler(dataReader.getDataProvider());

        // processes the data
        dataReader.readFile();
        // testing if the parsing was successful and correct -> check "parsedInputData.txt"
        dataReader.printData();


        DataProvider dataProvider = dataReader.getDataProvider();
        switch (userChoiceComputationMode) {
            case "classic":
                searchHandler.searchAllAnswers(dataProvider);
                break;
            case "topK":
                searchHandler.searchTopKAnswers(dataProvider, receive_k());
                break;
            case "threshold":
                searchHandler.searchThresholdAnswers(dataProvider, receiveThreshold());
                break;
            default:
                System.out.println("invalid input. restart and enter a valid input. Check ReadMe for more info.");
                break;
        }


        /**
         * previous user input with scanner
         */
        /*
        ascii_art();

        String userChoiceComputationMode = "";
        String userChoiceTransducerMode;
        String userChoiceFileInput;

        DataReader dataReader;
        Application.SearchHandler searchHandler;
        Scanner scanner = new Scanner(System.in);

        userChoiceFileInput = userInputData(scanner);
        userChoiceTransducerMode = userInputTransducer(scanner);
        userChoiceComputationMode = userInputComputation(scanner);


        if (userChoiceTransducerMode.equals("provided")) {
            dataReader = new DataReader(userChoiceFileInput, false);

        } else if (userChoiceTransducerMode.equals("generate")) {
            dataReader = new DataReader(userChoiceFileInput, true);
        } else { // error ...
            return;
        }

        searchHandler = new Application.SearchHandler(dataReader.getDataProvider());

        // processes the data
        dataReader.readFile();
        // testing if the parsing was successful and correct -> check "parsedInputData.txt"
        dataReader.printData();


        DataProvider dataProvider = dataReader.getDataProvider();
        switch (userChoiceComputationMode) {
            case "classic":
                searchHandler.searchAllAnswers(dataProvider);
                break;
            case "topK":
                searchHandler.searchTopKAnswers(dataProvider, receive_k());
                break;
            case "threshold":
                searchHandler.searchThresholdAnswers(dataProvider, receiveThreshold());
                break;
            default:
                System.out.println("invalid input. restart and enter a valid input. Check ReadMe for more info.");
                break;
        }

         */
    }


    public static int receive_k() {
        Scanner scannerK = new Scanner(System.in);
        System.out.print("Enter the desired value for k (int): ");
        return scannerK.nextInt();

    }

    public static Double receiveThreshold() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the desired threshold (Double): ");
        return scanner.nextDouble();
    }

    public static void ascii_art() {
        System.out.println();
        System.out.println("┌ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┐");
        System.out.println("|  _____  _                     __                |");
        System.out.println("| /__   \\| |__    ___   ___    / /   ___    __ _  |");
        System.out.println("|   / /\\/| '_ \\  / _ \\ / _ \\  / /   / _ \\  / _` | |");
        System.out.println("|  / /   | | | ||  __/| (_) |/ /___| (_) || (_| | |");
        System.out.println("|  \\/    |_| |_| \\___| \\___/ \\____/ \\___/  \\__, | |");
        System.out.println("|                                          |___/  |");
        System.out.println("└ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘");
        System.out.println();

    }

    public static String userInputData(Scanner scanner) {

        // receive the file name we want to compute
        System.out.println("Enter the file name you want to process (has to be stored in src/Application.main/resources/input/): ");

        return scanner.next();
    }

    public static String userInputComputation(Scanner scanner) {
        // receive the mode of the search
        System.out.println("Select the mode you want to start the application! Type ... \n" +
                "  'classic' for a complete search, \n" +
                "  'threshold' for a threshold search, \n" +
                "  'topK' for a topK search. ");

        return scanner.next();
    }

    public static String userInputTransducer(Scanner scanner) {
        // receive the transducer mode that is used
        System.out.println("Do you provide a transducer yourself or do you want to generate a transducer? type ... \n" +
                "  'generate' if you want to use an auto-generated transducer, \n" +
                "  'provided' if you provide a transducer on your own. ");

        return scanner.next();
    }


}

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
        String userChoiceParameter = "";

        if (args.length > 3) {
            userChoiceParameter = args[3];
        } else {
            userChoiceParameter = "0";
        }

        System.out.println(args[0] + " " + args[1] + " " + args[2]);

        DataReader dataReader;
        SearchHandler searchHandler;

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
                searchHandler.searchTopKAnswers(dataProvider, Integer.parseInt(userChoiceParameter));
                break;
            case "threshold":
                searchHandler.searchThresholdAnswers(dataProvider, Double.parseDouble(userChoiceParameter));
                break;
            default:
                System.out.println("invalid input. restart and enter a valid input. Check ReadMe for more info.");
                break;
        }

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


}

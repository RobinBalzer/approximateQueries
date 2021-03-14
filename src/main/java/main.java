import DataProvider.DataReader;

import java.util.Scanner;

public class main {

    /**
     * main function which is executed.
     * We then delegate the task further down to the SearchHandler.
     * If the user chooses a top k search, we also ask for the k value.
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        ascii_art();

        String userChoiceComputationMode = "";
        String userChoiceTransducerMode;
        String userChoiceFileInput;

        DataReader dataReader;
        SearchHandler searchHandler = new SearchHandler();
        Scanner scanner = new Scanner(System.in);

        // receive the file name we want to compute
        System.out.println("Enter the file name you want to process (has to be stored in src/main/resources/input/): ");
        userChoiceFileInput = scanner.next();

        // receive the transducer mode that is used
        System.out.println("Do you provide a transducer yourself or do you want to generate a transducer?");
        System.out.println("NOTE: If you choose to generate a transducer automatically it will \n (1) only preserve classical answers and \n (2) override your provided transducer");
        System.out.println("provide transducer yourself (y) or generate a transducer (n): ");
        userChoiceTransducerMode = scanner.next();

        if (userChoiceTransducerMode.matches("yes|y|si")) {
            dataReader = new DataReader(userChoiceFileInput, false);
        } else {
            dataReader = new DataReader(userChoiceFileInput, true);
        }

        dataReader.readFile();
        // TODO: read and provide data...

        // receive the mode of the search
        System.out.println("Enter the mode you want to start the application! current supported modes: 'searchAll', 'searchTopK_LO', 'searchTopK_GO'. ");
        userChoiceComputationMode = scanner.next();

        switch (userChoiceComputationMode) {
            case "searchAll":
                searchHandler.searchAllAnswers();
                break;
            case "searchTopK_LO":
                searchHandler.searchTopKAnswersLocallyOptimized(receive_k());
                break;
            case "searchTopK_GO":
                searchHandler.searchTopKAnswersGloballyOptimized(receive_k());
                break;
            default:
                System.out.println("invalid input. restart and enter a valid input. Check ReadMe for more info.");
                break;
        }
    }

    public static int receive_k() {
        Scanner scannerK = new Scanner(System.in);
        System.out.println("You've chosen a top k search. Please enter your desired value for k: ");
        return scannerK.nextInt();

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
        System.out.println();
        System.out.println();

    }


}

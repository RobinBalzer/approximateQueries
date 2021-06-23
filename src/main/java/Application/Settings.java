package Application;

public class Settings {

    public static String inputFileDirectory = "src/main/resources/input/"; // use this for Intellij runs
    // public static String inputFileDirectory = "resources/input/"; // use this for building the .jar

    public static String outputFileDirectory = "src/main/resources/output/"; // use this for Intellij runs
    // public static String outputFileDirectory = "resources/output/"; // use this for building the .jar

    public static int numberOfMaxNodesPossible;
    public static int numberOfActualNodes;

    public static long preprocessingTime;
    public static long dijkstraProcessingTime;
    public static long combinedTime;
    public static long postprocessingTime;

    public static int numberOfAnswers;

    public static long getPreprocessingTime() {
        return preprocessingTime;
    }

    public static void setPreprocessingTime(long preprocessingTime) {
        Settings.preprocessingTime = preprocessingTime;
    }

    public static long getDijkstraProcessingTime() {
        return dijkstraProcessingTime;
    }

    public static void setDijkstraProcessingTime(long dijkstraProcessingTime) {
        Settings.dijkstraProcessingTime = dijkstraProcessingTime;
    }

    public static long getPostprocessingTime() {
        return postprocessingTime;
    }

    public static void setPostprocessingTime(long postprocessingTime) {
        Settings.postprocessingTime = postprocessingTime;
    }

    public static long getCombinedTime() {
        return combinedTime;
    }

    public static void setCombinedTime(long combinedTime) {
        Settings.combinedTime = combinedTime;
    }

    public static int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public static void setNumberOfAnswers(int numberOfAnswers) {
        Settings.numberOfAnswers = numberOfAnswers;
    }

    public static int getNumberOfMaxNodesPossible() {
        return numberOfMaxNodesPossible;
    }

    public static void setNumberOfMaxNodesPossible(int numberOfMaxNodesPossible) {
        Settings.numberOfMaxNodesPossible = numberOfMaxNodesPossible;
    }

    public static int getNumberOfActualNodes() {
        return numberOfActualNodes;
    }

    public static void setNumberOfActualNodes(int numberOfActualNodes) {
        Settings.numberOfActualNodes = numberOfActualNodes;
    }
}

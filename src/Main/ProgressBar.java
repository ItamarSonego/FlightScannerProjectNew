package Main;

public class ProgressBar {

    public static void updateProgressBar(int currentProgress, int totalTasks, int progressBarWidth) {
        int progressPercentage = (currentProgress * 100) / totalTasks;
        int numCompletedBlocks = (currentProgress * progressBarWidth) / totalTasks;
        int numRemainingBlocks = progressBarWidth - numCompletedBlocks;

        StringBuilder progressBar = new StringBuilder("");
        for (int i = 0; i < numCompletedBlocks; i++) {
            progressBar.append("█");
        }
        for (int i = 0; i < numRemainingBlocks; i++) {
            progressBar.append(" ");
        }
        progressBar.append(" " + progressPercentage + "%");

        System.out.print("\r" + progressBar.toString()); // Use \r for carriage return to update progress
    }
}
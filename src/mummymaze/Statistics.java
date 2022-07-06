package mummymaze;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import agent.Heuristic;
import agent.Solution;
import gui.MainFrame;
import searchmethods.BeamSearch;
import searchmethods.DepthLimitedSearch;
import searchmethods.IDAStarSearch;
import searchmethods.InformedSearch;
import searchmethods.SearchMethod;

public class Statistics {


    public static void createStatistics(
            MummyMazeAgent agent,
            JTextArea textArea,
            MainFrame frame
    ) {
        StatisticsFile statisticsFile = new StatisticsFile();
        File file = statisticsFile.getFile();
        File levelsFolderFile = statisticsFile.getLevelFolderFile();
        boolean t = false;
        List<String> resultLines = new LinkedList<>();
        if (file != null && levelsFolderFile != null) {
            textArea.setText("");
            SearchMethod[] allSearchMethods = agent.getSearchMethodsArray();
            Solution solution;
            try {
                long initialTime = System.nanoTime();
                for (File levelFile : levelsFolderFile.listFiles()) {
                    agent.readInitialStateFromFile(levelFile);
                    textArea.append("\n\n" + levelFile.getName() + "\n");
                    resultLines.add(
                            "\r\n\r\n" + levelFile.getName() + "\r\n");
                    resultLines.add(
                            "Search method" + "\t" + "Heuristic" + "\t" +
                                    "Found solution" + "\t" +
                                    "Solution Cost" +
                                    "\t" + "Num Expanded Nodes" + "\t" +
                                    "Max frontier size" + "\t" +
                                    "Num generated states" + "\t" +
                                    "Time (seconds)" + "\t" +
                                    "Time (nanoseconds)" + "\r\n");
                    for (SearchMethod searchMethod : allSearchMethods) {
                        textArea.append(searchMethod.toString() + "\n");
                        MummyMazeProblem problem = new MummyMazeProblem(
                                agent.getEnvironment()
                                        .clone());
                        agent.setSearchMethod(searchMethod);
                        if (searchMethod instanceof InformedSearch ||
                                searchMethod.getClass() ==
                                        IDAStarSearch.class) {
                            if (searchMethod instanceof BeamSearch) {
                                ((BeamSearch) searchMethod).setBeamSize(50);
                            }
                            for (Heuristic heuristic : agent.getHeuristicsArray()) {
                                agent.setHeuristic(heuristic);
                                long startTime = System.nanoTime();
                                solution = agent.solveProblem(problem);
                                long endTime = System.nanoTime();
                                long nanosecondsElapsed = (endTime -
                                        startTime);
                                long secondsElapsed = TimeUnit.SECONDS.convert(
                                        nanosecondsElapsed,
                                        TimeUnit.NANOSECONDS
                                );
                                if (agent.hasSolution()) {
                                    resultLines.add(
                                            searchMethod.toString() + "\t" +
                                                    heuristic.toString() +
                                                    "\t" + "Yes" + "\t" +
                                                    solution.getCost() +
                                                    "\t" +
                                                    searchMethod.getStatistics().numExpandedNodes +
                                                    "\t" +
                                                    searchMethod.getStatistics().maxFrontierSize +
                                                    "\t" +
                                                    searchMethod.getStatistics().numGeneratedSates +
                                                    "\t" + secondsElapsed +
                                                    "\t" +
                                                    nanosecondsElapsed);
                                } else {
                                    resultLines.add(
                                            searchMethod.toString() + "\t" +
                                                    heuristic.toString() +
                                                    "\t" + "No");
                                }
                                resultLines.add("\r\n");
                                agent.resetEnvironment();
                            }
                        } else {
                            if (searchMethod instanceof DepthLimitedSearch) {
                                ((DepthLimitedSearch) searchMethod).setLimit(50);
                            }
                            long startTime = System.nanoTime();
                            solution = agent.solveProblem(problem);
                            long endTime = System.nanoTime();
                            long nanosecondsElapsed = (endTime -
                                    startTime);
                            long secondsElapsed = TimeUnit.SECONDS.convert(
                                    nanosecondsElapsed,
                                    TimeUnit.NANOSECONDS
                            );
                            if (agent.hasSolution()) {
                                resultLines.add(
                                        searchMethod.toString() + "\t" +
                                                "-" +
                                                "\t" + "Yes" + "\t" +
                                                solution.getCost() + "\t" +
                                                searchMethod.getStatistics().numExpandedNodes +
                                                "\t" +
                                                searchMethod.getStatistics().maxFrontierSize +
                                                "\t" +
                                                searchMethod.getStatistics().numGeneratedSates +
                                                "\t" + secondsElapsed +
                                                "\t" +
                                                nanosecondsElapsed);
                            } else {
                                resultLines.add(
                                        searchMethod.toString() + "\t" +
                                                "-" +
                                                "\t" + "No");
                            }
                            resultLines.add("\r\n");
                            agent.resetEnvironment();
                        }
                    }
                }
                statisticsFile.appendStringListToFile(resultLines);
                long endTime = System.nanoTime();
                long totalSecondsElapsed = TimeUnit.SECONDS.convert(
                        endTime - initialTime,
                        TimeUnit.NANOSECONDS
                );
                JOptionPane.showMessageDialog(
                        frame,
                        "Statistics created with success! \nTotal time (seconds): " +
                                totalSecondsElapsed
                );
            } catch (Exception error) {
                System.err.println(error.getMessage());
            }
        }
    }
}

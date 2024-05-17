package prereqchecker;

import java.util.*;

/**
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        ArrayList<classes> allCourses = AdjList.createCourseList();
        //System.out.println(allCourses.size());
        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);

        ArrayList<String> eligibleCourses = new ArrayList<String>();
        ArrayList<String> classesTaken = new ArrayList<String>();

        int numOfCourses = StdIn.readInt();
        String courseTitle = StdIn.readLine();

        

        for (int i = 0; i < numOfCourses; i++) {    //collects all the prereqs
            courseTitle = StdIn.readLine();
            for (int j = 0; j < allCourses.size(); j++) {
                classes tempcourse = allCourses.get(j);
                //System.out.println(tempcourse.getName());
                if (tempcourse.getName().equals(courseTitle)) {
                    classesTaken.add(courseTitle);
                    //System.out.println("first: " +courseTitle);
                    for (int k = 0; k < tempcourse.getPreReqs().size(); k++) {
                        classesTaken.add(tempcourse.getPreReqs().get(k));
                        //System.out.println(tempcourse.getPreReqs().get(k));

                        // String reqChain = tempcourse.getPreReqs().get(k);
                        // boolean match = true;
                        // while (match = true) {
                        //     match = false;
                        //     classes newtemp = null;
                        //     for (int l = 0; l < allCourses.size(); l++) {
                        //         newtemp = allCourses.get(l);
                        //         if (newtemp.getName().equals(reqChain)) {
                        //             for (int z = 0; z < newtemp.getPreReqs().size(); z++) {

                        //             }
                        //         }

                        //     }
                        // }
                        
                    }
                }
            }
        }

        //now i have to go through every course and compare its prereqs to the courses in the classesTaken
        //then add all of said courses into the eligibleCourses
        
        for (int i = 0; i < allCourses.size(); i++) {
            classes tempcourse = allCourses.get(i);

            for (int j = 0; j < tempcourse.getPreReqs().size(); j++) {
                int correctPreReqs = 0;
                for (int k = 0; k < classesTaken.size(); k++) {
                    if (tempcourse.getPreReqs().get(j).equals(classesTaken.get(k))) {
                        correctPreReqs++;
                    }
                }

                if (correctPreReqs == tempcourse.getPreReqs().size()) {
                    eligibleCourses.add(tempcourse.getName());
                }
            }
        }

        //afterward go through the eligible courses and compare them to the courses taken
        // if they are the same then it deletes them
        for (int i = 0; i < eligibleCourses.size(); i++) {
            for (int j = 0; j < classesTaken.size(); j++) {
                if (eligibleCourses.get(i).equals(classesTaken.get(j))) {
                    eligibleCourses.remove(i);
                    //break;
                }
            }
        }

        //lastly print al of the eligible courses to the out
        for (int i = 0; i < eligibleCourses.size(); i++) {

            StdOut.print(eligibleCourses.get(i));
            StdOut.println();
        }
        
    }
}

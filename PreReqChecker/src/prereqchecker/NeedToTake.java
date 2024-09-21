package prereqchecker;

import java.util.*;

/**
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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

	// WRITE YOUR CODE HERE
    StdIn.setFile(args[0]);
    StdOut.setFile(args[2]);
    int num = Integer.parseInt(StdIn.readLine());
    Digraph digraph = new Digraph();
    for (int i = 0; i < num; i++) {
        digraph.addCourse(StdIn.readLine());
    }
    int prereqs = Integer.parseInt(StdIn.readLine());
    for (int i = 0; i < prereqs; i++) {
        String[] sep = StdIn.readLine().split(" ");
        String ind = sep[0];
        String pr = sep[1];
        digraph.addPreReq(ind, pr);
    }

    ArrayList<String> completed = new ArrayList<>();
    StdIn.setFile(args[1]);
    String target = StdIn.readLine();
    int numTaken = Integer.parseInt(StdIn.readLine());
    for (int i = 0; i < numTaken; i++) {
        completed.add(StdIn.readLine());
    }
    completed = digraph.addPreReqs(completed);

    ArrayList<String> targetPreReqs = new ArrayList<>();
    targetPreReqs.add(target);
    targetPreReqs = digraph.addPreReqs(targetPreReqs);
    targetPreReqs.remove(target);
    ArrayList<String> needToTake = new ArrayList<>();

    for (int i = 0; i < targetPreReqs.size(); i++) {
        if (!completed.contains(targetPreReqs.get(i))) {
            needToTake.add(targetPreReqs.get(i));
        }
    }

    for (int i = 0; i < needToTake.size(); i++) {
        StdOut.println(needToTake.get(i));
    }
    }
}

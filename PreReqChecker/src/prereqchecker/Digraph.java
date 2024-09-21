package prereqchecker;

import java.util.*;

public class Digraph {
    private HashMap<String, LinkedList<String>> digraph;
    
    public Digraph() {
        digraph = new HashMap<String, LinkedList<String>>();
    }

    public void addCourse(String str) {
        digraph.put(str, new LinkedList<String>());
    }

    public void addPreReq(String course,String prereq) {
       digraph.get(course).add(prereq);
    }

    public LinkedList<String> getPreReqs(String course) {
       return digraph.get(course);
    }

    public void print(){
        Object[] courses = digraph.keySet().toArray();
        for (int i = 0; i < courses.length; i++) {
            StdOut.print(courses[i]);
            LinkedList<String> prereqs = digraph.get(courses[i]);
            for (int j = 0; j < prereqs.size(); j++) {
                StdOut.print(" " + prereqs.get(j));
            }  
        StdOut.print("\n");    
        }
    }

    public boolean checkValidPrereq(String course, String proposed) {
        LinkedList<String> prereqs = digraph.get(proposed);
        
        if (prereqs.contains(course)) {
            return false;
        } else if (prereqs == null) {
            return true;
        } else {
            boolean bool = true;
            for (int i = 0; i < prereqs.size(); i++) {
                if (!checkValidPrereq(course, prereqs.get(i))) {
                    bool = false;
                }
            }

            return bool;
        }
    }

    public ArrayList<String> addPreReqs(ArrayList<String> list) {
        int i = 0;
        while (i < list.size()) {
            LinkedList<String> prereqs = digraph.get(list.get(i));
            if (prereqs != null) {
                for (int j = 0; j < prereqs.size(); j++) {
                if (!list.contains(prereqs.get(j))) {
                    list.add(prereqs.get(j));
                }
            }
            }
            i++;
        }

        return list;
    }

    public ArrayList<String> addEligible(ArrayList<String> completed) {
        ArrayList<String> eligible = new ArrayList<>();
        for (int i = 0; i < digraph.size(); i++) {
            Boolean bool = true;
            String course = (String) digraph.keySet().toArray()[i];
            if (!completed.contains(course)) {
                LinkedList<String> prereqs = digraph.get(course);
                if (prereqs != null) {
                    for (int j = 0; j < prereqs.size(); j++) {
                        if (!completed.contains(prereqs.get(j))) {
                            bool = false;
                            break;
                        }
                    }
                }
                if (bool) {
                   eligible.add(course);
                }
            }
        }
        return eligible;
    }
}

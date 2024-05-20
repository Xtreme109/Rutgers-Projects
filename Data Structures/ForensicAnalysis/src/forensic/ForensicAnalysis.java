package forensic;

import java.util.ArrayList;
// import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;

/**
 * This class represents a forensic analysis system that manages DNA data using
 * BSTs.
 * Contains methods to create, read, update, delete, and flag profiles.
 * 
 * @author Kal Pandit
 */
public class ForensicAnalysis {

    private TreeNode treeRoot;            // BST's root
    private String firstUnknownSequence;
    private String secondUnknownSequence;

    public ForensicAnalysis () {
        treeRoot = null;
        firstUnknownSequence = null;
        secondUnknownSequence = null;
    }

    /**
     * Builds a simplified forensic analysis database as a BST and populates unknown sequences.
     * The input file is formatted as follows:
     * 1. one line containing the number of people in the database, say p
     * 2. one line containing first unknown sequence
     * 3. one line containing second unknown sequence
     * 2. for each person (p), this method:
     * - reads the person's name
     * - calls buildSingleProfile to return a single profile.
     * - calls insertPerson on the profile built to insert into BST.
     *      Use the BST insertion algorithm from class to insert.
     * 
     * DO NOT EDIT this method, IMPLEMENT buildSingleProfile and insertPerson.
     * 
     * @param filename the name of the file to read from
     */
    public void buildTree(String filename) {
        // DO NOT EDIT THIS CODE
        StdIn.setFile(filename); // DO NOT remove this line

        // Reads unknown sequences
        String sequence1 = StdIn.readLine();
        firstUnknownSequence = sequence1;
        String sequence2 = StdIn.readLine();
        secondUnknownSequence = sequence2;
        
        int numberOfPeople = Integer.parseInt(StdIn.readLine()); 

        for (int i = 0; i < numberOfPeople; i++) {
            // Reads name, count of STRs
            String fname = StdIn.readString();
            String lname = StdIn.readString();
            String fullName = lname + ", " + fname;
            // Calls buildSingleProfile to create
            Profile profileToAdd = createSingleProfile();
            // Calls insertPerson on that profile: inserts a key-value pair (name, profile)
            insertPerson(fullName, profileToAdd);
        }
    }

    /** 
     * Reads ONE profile from input file and returns a new Profile.
     * Do not add a StdIn.setFile statement, that is done for you in buildTree.
    */
    public Profile createSingleProfile() {
        int numberofstrs = StdIn.readInt();
        STR list[] = new STR[numberofstrs];

        for (int i = 0; i < numberofstrs; i++) {
            String strname = StdIn.readString();
            int stroccurances = StdIn.readInt();

            STR data = new STR(strname, stroccurances);

            list[i] = data;
        }
        
        Profile a = new Profile(list);
        
        
        return a; // update this line
    }

    /**
     * Inserts a node with a new (key, value) pair into
     * the binary search tree rooted at treeRoot.
     * 
     * Names are the keys, Profiles are the values.
     * USE the compareTo method on keys.
     * 
     * @param newProfile the profile to be inserted
     */
    public void insertPerson(String name, Profile newProfile) {
        TreeNode axe = new TreeNode(name, newProfile, null, null);

        TreeNode prior = null;
        TreeNode ptr = treeRoot;

        if (treeRoot == null) {
            treeRoot = axe;
        } else if (treeRoot != null) {
            while (ptr != null) {
                int cmp = name.compareTo(ptr.getName());
                if (cmp < 0) {
                    prior = ptr;
                    ptr = ptr.getLeft();
                } else if (cmp > 0) {
                    prior = ptr;
                    ptr = ptr.getRight();
                } else if (cmp == 0) {
                    break;
                }

                if (ptr == null) {
                    if (cmp < 0) {
                        prior.setLeft(axe);
                    } else if (cmp > 0) {
                        prior.setRight(axe);
                    }
                }
            }
        }
        
        
    }

    /**
     * Finds the number of profiles in the BST whose interest status matches
     * isOfInterest.
     *
     * @param isOfInterest the search mode: whether we are searching for unmarked or
     *                     marked profiles. true if yes, false otherwise
     * @return the number of profiles according to the search mode marked
     */

     private int interestcount(TreeNode root, boolean interest) {
        int total=0; 

        if (root == null) {
            return 0;
        }

        if (root.getProfile().getMarkedStatus() == interest) {
            total++;
        }

        total = total + interestcount(root.getLeft(), interest);
        total = total + interestcount(root.getRight(), interest);
        
        return total;
    }

    public int getMatchingProfileCount(boolean isOfInterest) {
        int markednum = interestcount(treeRoot, isOfInterest);

        return markednum; // update this line
    }

    /**
     * Helper method that counts the # of STR occurrences in a sequence.
     * Provided method - DO NOT UPDATE.
     * 
     * @param sequence the sequence to search
     * @param STR      the STR to count occurrences of
     * @return the number of times STR appears in sequence
     */
    private int numberOfOccurrences(String sequence, String STR) {
        
        // DO NOT EDIT THIS CODE
        
        int repeats = 0;
        // STRs can't be greater than a sequence
        if (STR.length() > sequence.length())
            return 0;
        
            // indexOf returns the first index of STR in sequence, -1 if not found
        int lastOccurrence = sequence.indexOf(STR);
        
        while (lastOccurrence != -1) {
            repeats++;
            // Move start index beyond the last found occurrence
            lastOccurrence = sequence.indexOf(STR, lastOccurrence + STR.length());
        }
        return repeats;
    }

    /**
     * Traverses the BST at treeRoot to mark profiles if:
     * - For each STR in profile STRs: at least half of STR occurrences match (round
     * UP)
     * - If occurrences THROUGHOUT DNA (first + second sequence combined) matches
     * occurrences, add a match
     */


     private ArrayList<TreeNode> travel(TreeNode root, ArrayList<TreeNode> array) {
        if (root == null) {
            return null;
        }

        array.add(root);

        travel(root.getLeft(), array);
        travel(root.getRight(), array);

        return array;
    }

    public void flagProfilesOfInterest() {

        ArrayList<TreeNode> list = new ArrayList<>();
        travel(treeRoot, list);

        for (int i = 0; i < list.size(); i++) {
            STR[] a = list.get(i).getProfile().getStrs();
            double compute = a.length/2; 
            double half = Math.ceil(compute);
            int copycount = 0;          

            for (int j = 0; j < a.length; j++) {
                String name = a[j].getStrString();
                int nodeOccurs = a[j].getOccurrences();
                int seqOccurs = 0;
                
                String newsequence = firstUnknownSequence+secondUnknownSequence;
                Pattern p = Pattern.compile(name);
                Matcher m = p.matcher(newsequence);
                while (m.find()) {
                    seqOccurs++;
                }

                if (seqOccurs == nodeOccurs) {
                    copycount++;
                }
            }

            if (copycount >= half) {
                list.get(i).getProfile().setInterestStatus(true);
            }
        }
    }

    /**
     * Uses a level-order traversal to populate an array of unmarked Strings representing unmarked people's names.
     * - USE the getMatchingProfileCount method to get the resulting array length.
     * - USE the provided Queue class to investigate a node and enqueue its
     * neighbors.
     * 
     * @return the array of unmarked people
     */
    public String[] getUnmarkedPeople() {


        int arrlength = getMatchingProfileCount(false);
        // System.out.println(arrlength);
        String[] a = new String[arrlength];
        

        Queue<TreeNode> q = new Queue<>();
        int placement = 0;
        q.enqueue(treeRoot); //Enqueue the root
        // Keep going until we run out of nodes to print
        while (!q.isEmpty()) {
            //Store the dequeued node
            TreeNode temp = q.dequeue();

            // System.out.println("temp: " + temp.getName());
            if (temp.getProfile().getMarkedStatus() == false) {
                // System.out.println("yo");
                // System.out.println("placement: "+ placement);
                a[placement] = temp.getName();
                placement++;
            }

            //Enqueue the left and right if they're not null
            if (temp.getLeft() != null) q.enqueue(temp.getLeft());
            if (temp.getRight() != null) q.enqueue(temp.getRight());
        }

        return a; // update this line
    }

    /**
     * Removes a SINGLE node from the BST rooted at treeRoot, given a full name (Last, First)
     * This is similar to the BST delete we have seen in class.
     * 
     * If a profile containing fullName doesn't exist, do nothing.
     * You may assume that all names are distinct.
     * 
     * @param fullName the full name of the person to delete
     */

    private TreeNode deleteperson(TreeNode root, String name) {

        if (root == null) {
            return root;
        }

        int cmp = name.compareTo(root.getName());
        if (cmp < 0) {
            root.setLeft(deleteperson(root.getLeft(), name));
            return root;
        } else if (cmp > 0) {
            root.setRight(deleteperson(root.getRight(), name));
            return root;
        }


        if (root.getLeft() == null) {
            TreeNode temp = root.getRight();
            return temp;
        } else if (root.getRight() == null) {
            TreeNode temp = root.getLeft();
            return temp;
        }
 
        // If both children exist
        else {
 
            TreeNode succParent = root;
 
            // Find successor
            TreeNode succ = root.getRight();
            while (succ.getLeft() != null) {
                succParent = succ;
                succ = succ.getLeft();
            }
 
            // Delete successor.  Since successor
            // is always left child of its parent
            // we can safely make successor's right
            // right child as left of its parent.
            // If there is no succ, then assign
            // succ.right to succParent.right
            if (succParent != root) {
                succParent.setLeft(succ.getRight());
            } else {
                succParent.setRight(succ.getRight());
            }
            // Copy Successor Data to root
            // root.setProfile(succ.getProfile());
            root.setName(succ.getName());

            
 
            // Delete Successor and return root
            return root;
        }
    }

    public void removePerson(String fullName) {
        
        deleteperson(treeRoot, fullName);
        
    }

    /**
     * Clean up the tree by using previously written methods to remove unmarked
     * profiles.
     * Requires the use of getUnmarkedPeople and removePerson.
     */
    public void cleanupTree() {
        // WRITE YOUR CODE HERE

    }

    /**
     * Gets the root of the binary search tree.
     *
     * @return The root of the binary search tree.
     */
    public TreeNode getTreeRoot() {
        return treeRoot;
    }

    /**
     * Sets the root of the binary search tree.
     *
     * @param newRoot The new root of the binary search tree.
     */
    public void setTreeRoot(TreeNode newRoot) {
        treeRoot = newRoot;
    }

    /**
     * Gets the first unknown sequence.
     * 
     * @return the first unknown sequence.
     */
    public String getFirstUnknownSequence() {
        return firstUnknownSequence;
    }

    /**
     * Sets the first unknown sequence.
     * 
     * @param newFirst the value to set.
     */
    public void setFirstUnknownSequence(String newFirst) {
        firstUnknownSequence = newFirst;
    }

    /**
     * Gets the second unknown sequence.
     * 
     * @return the second unknown sequence.
     */
    public String getSecondUnknownSequence() {
        return secondUnknownSequence;
    }

    /**
     * Sets the second unknown sequence.
     * 
     * @param newSecond the value to set.
     */
    public void setSecondUnknownSequence(String newSecond) {
        secondUnknownSequence = newSecond;
    }

}

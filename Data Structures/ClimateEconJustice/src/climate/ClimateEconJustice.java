package climate;

import java.util.ArrayList;

import javax.swing.plaf.nimbus.State;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        StateNode ptr = firstState;
        StateNode newNode = new StateNode();

        String [] a = inputLine.split(",");


        if (firstState == null) {
            newNode.setName(a[2]);
            firstState = newNode;
        } else {
            while (ptr != null) {
                if (ptr.getName().equals(a[2])) {
                    break;
                } if (ptr.getNext() == null) {
                    newNode.setName(a[2]);
                    ptr.setNext(newNode);
                    break;
                }
                ptr = ptr.getNext();
            }
        }
        
        

        

    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        // System.out.println("yo?");
        String [] a = inputLine.split(",");
        StateNode ptr = firstState;
        CountyNode newCounty = new CountyNode();

        while (ptr != null) {
            
            if (ptr.getName().equals(a[2])) {
                // System.out.println("State: " + a[2]);
                CountyNode countyptr = ptr.getDown();

                if (countyptr == null) {
                    // System.out.println("County: "+ a[1]);
                    // System.out.println();
                    newCounty.setName(a[1]);
                    ptr.setDown(newCounty);
                    countyptr = newCounty;
                } else {
                    // System.out.println("County: "+ a[1]);
                    while (countyptr != null) {
                        // System.out.println("County: "+ a[1]);
                        if (countyptr.getName().equals(a[1])) {
                            break;
                        } if (countyptr.getNext() == null) {
                            newCounty.setName(a[1]);
                            countyptr.setNext(newCounty);
                            break;
                        }
                        countyptr = countyptr.getNext();
                    }
                }
                break;
            }

            ptr = ptr.getNext();
        }


    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        String [] a = inputLine.split(",");
        StateNode ptr = firstState;
        CommunityNode newCommunity = new CommunityNode();

        while (ptr != null) {
            
            if (ptr.getName().equals(a[2])) {
                // System.out.println("State: " + a[2]);
                CountyNode countyptr = ptr.getDown();

                while (countyptr != null) {
                    if (countyptr.getName().equals(a[1])) {
                        CommunityNode commptr = countyptr.getDown();

                        if (commptr == null) {
                           newCommunity.setName(a[0]);
                           Data b = new Data();
                           b.setPrcntAfricanAmerican(Double.parseDouble(a[3]));
                           b.setPrcntNative(Double.parseDouble(a[4]));
                           b.setPrcntAsian(Double.parseDouble(a[5]));
                           b.setPrcntWhite(Double.parseDouble(a[8]));
                           b.setPrcntHispanic(Double.parseDouble(a[9]));
                           b.setAdvantageStatus(a[19]);
                           b.setPMlevel(Double.parseDouble(a[49]));
                           b.setChanceOfFlood(Double.parseDouble(a[37]));
                           b.setPercentPovertyLine(Double.parseDouble(a[121]));
                           newCommunity.setInfo(b);
                           countyptr.setDown(newCommunity);
                           commptr = newCommunity;
                        } else {
                            while (commptr != null) {
                                // System.out.println("County: "+ a[1]);
                                if (commptr.getName().equals(a[0])) {
                                    break;
                                } if (commptr.getNext() == null) {
                                    newCommunity.setName(a[0]);
                                    Data b = new Data();
                                    b.setPrcntAfricanAmerican(Double.parseDouble(a[3]));
                                    b.setPrcntNative(Double.parseDouble(a[4]));
                                    b.setPrcntAsian(Double.parseDouble(a[5]));
                                    b.setPrcntWhite(Double.parseDouble(a[8]));
                                    b.setPrcntHispanic(Double.parseDouble(a[9]));
                                    b.setAdvantageStatus(a[19]);
                                    b.setPMlevel(Double.parseDouble(a[49]));
                                    b.setChanceOfFlood(Double.parseDouble(a[37]));
                                    b.setPercentPovertyLine(Double.parseDouble(a[121]));
                                    newCommunity.setInfo(b);
                                    commptr.setNext(newCommunity);
                                    break;
                                }
                                commptr = commptr.getNext();
                            }
                        }

                        break;
                    }

                    countyptr = countyptr.getNext();
                }

                break;
            }

            ptr = ptr.getNext();
        }

    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race) {

        StateNode ptr = firstState;
        int poorcomms = 0;
        // System.out.println("race: " + race);

        while (ptr != null) {
            CountyNode countyptr = ptr.getDown();

            while (countyptr != null) {
                CommunityNode commptr = countyptr.getDown();

                while (commptr != null) {
                    // System.out.println("community name: " + commptr.getName());
                    // System.out.println("race: " + race);
                    // System.out.println(commptr.getInfo().getPrcntAfricanAmerican());
                    // System.out.println();
                    if (race.equals("African American")) {
                        // System.out.println("It works");
                        if ((commptr.getInfo().getPrcntAfricanAmerican() * 100 >= userPrcntage) && (commptr.getInfo().getAdvantageStatus().equals("True"))) {
                            poorcomms++;
                        }
                    } else if (race.equals("Native American")) {
                        if ((commptr.getInfo().getPrcntNative() * 100 >= userPrcntage) && (commptr.getInfo().getAdvantageStatus().equals("True"))) {
                            poorcomms++;
                        }
                    } else if (race.equals("Asian American")) {
                        if ((commptr.getInfo().getPrcntAsian() * 100 >= userPrcntage) && (commptr.getInfo().getAdvantageStatus().equals("True"))) {
                            poorcomms++;
                        }
                    } else if (race.equals("White American")) {
                        if ((commptr.getInfo().getPrcntWhite() * 100 >= userPrcntage) && (commptr.getInfo().getAdvantageStatus().equals("True"))) {
                            poorcomms++;
                        }
                    } else if (race.equals("Hispanic American")) {
                        if ((commptr.getInfo().getPrcntHispanic() * 100 >= userPrcntage) && (commptr.getInfo().getAdvantageStatus().equals("True"))) {
                            poorcomms++;
                        }
                    }

                    commptr = commptr.getNext();
                }

                countyptr = countyptr.getNext();
            }

            ptr = ptr.getNext();
        }

        return poorcomms; // replace this line
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        StateNode ptr = firstState;
        int normalcomms = 0;
        // System.out.println("race: " + race);

        while (ptr != null) {
            CountyNode countyptr = ptr.getDown();

            while (countyptr != null) {
                CommunityNode commptr = countyptr.getDown();

                while (commptr != null) {
                    // System.out.println("community name: " + commptr.getName());
                    // System.out.println("race: " + race);
                    // System.out.println(commptr.getInfo().getPrcntAfricanAmerican());
                    // System.out.println();
                    if (race.equals("African American")) {
                        // System.out.println("It works");
                        if ((commptr.getInfo().getPrcntAfricanAmerican() * 100 >= userPrcntage) && (commptr.getInfo().getAdvantageStatus().equals("False"))) {
                            normalcomms++;
                        }
                    } else if (race.equals("Native American")) {
                        if ((commptr.getInfo().getPrcntNative() * 100 >= userPrcntage) && (commptr.getInfo().getAdvantageStatus().equals("False"))) {
                            normalcomms++;
                        }
                    } else if (race.equals("Asian American")) {
                        if ((commptr.getInfo().getPrcntAsian() * 100 >= userPrcntage) && (commptr.getInfo().getAdvantageStatus().equals("False"))) {
                            normalcomms++;
                        }
                    } else if (race.equals("White American")) {
                        if ((commptr.getInfo().getPrcntWhite() * 100 >= userPrcntage) && (commptr.getInfo().getAdvantageStatus().equals("False"))) {
                            normalcomms++;
                        }
                    } else if (race.equals("Hispanic American")) {
                        if ((commptr.getInfo().getPrcntHispanic() * 100 >= userPrcntage) && (commptr.getInfo().getAdvantageStatus().equals("False"))) {
                            normalcomms++;
                        }
                    }

                    commptr = commptr.getNext();
                }

                countyptr = countyptr.getNext();
            }

            ptr = ptr.getNext();
        }

        return normalcomms; // replace this line
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        StateNode ptr = firstState;
        ArrayList<StateNode> a = new ArrayList<StateNode>();

        while (ptr != null) {
            CountyNode countyptr = ptr.getDown();

            while (countyptr != null) {
                CommunityNode commptr = countyptr.getDown();
                boolean found = false;

                while (commptr != null) {
                    
                    if (commptr.getInfo().getPMlevel() >= PMlevel) {
                        a.add(ptr);
                        found = true;
                        break;
                    }

                    commptr = commptr.getNext();
                }

                if (found == true) {
                    break;
                }

                countyptr = countyptr.getNext();
            }

            ptr = ptr.getNext();
        }

        return a; // replace this line
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        StateNode ptr = firstState;
        int floods = 0;

        while (ptr != null) {
            CountyNode countyptr = ptr.getDown();

            while (countyptr != null) {
                CommunityNode commptr = countyptr.getDown();

                while (commptr != null) {
                    
                    if (commptr.getInfo().getChanceOfFlood() >= userPercntage) {        
                        floods++;
                    }

                    commptr = commptr.getNext();
                }

                countyptr = countyptr.getNext();
            }

            ptr = ptr.getNext();
        }

        return floods; // replace this line
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        StateNode ptr = firstState;
        ArrayList<CommunityNode> a = new ArrayList<CommunityNode>();

        while (ptr != null) {

            if (ptr.getName().equals(stateName)) {
                CountyNode countyptr = ptr.getDown();
                int communities = 0;

                while (countyptr != null) {
                    CommunityNode commptr = countyptr.getDown();

                    if (communities < 10) {
                        while (commptr != null) {
                            a.add(commptr);
                            communities++;
    
                            commptr = commptr.getNext();
    
                            if (communities == 10) {
                                break;
                            }
                        }
                    }
                    
                    int lowestpovertyindex = 0;
                    if (communities == 10) {
                        while (commptr != null) {
                            for (int i = 0; i < 10; i++) {
                                if (a.get(i).getInfo().getPercentPovertyLine() < a.get(lowestpovertyindex).getInfo().getPercentPovertyLine()) {
                                    lowestpovertyindex = i;
                                }
                            }

                            if (commptr.getInfo().getPercentPovertyLine() > a.get(lowestpovertyindex).getInfo().getPercentPovertyLine()) {
                                a.set(lowestpovertyindex, commptr);
                            }

                            commptr = commptr.getNext();
                        }
                    }


                    countyptr = countyptr.getNext();
                }

                break;
            }

            ptr = ptr.getNext();
        }

        return a;
    }
}
    

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FindPlan {
    ArrayList<State> plan;
    static int[] goalStateStructure = new int[] {1,2,3,4,5,6,7,8,0};

    public static void main(String[] args) {

        State initialState1 = new State(new int[] {8,6,7,2,5,4,3,0,1}, null);
        State initialState2 = new State(new int[] {6,4,7,8,5,0,3,2,1}, null);

        //FindAstarPlan(initialState2);
        if(FindDepthFirstPlan(initialState1))
            System.out.println("Plan found!");
        else
        System.out.println("Plan not found!");

    }

    public static boolean FindDepthFirstPlan(State initialState){
        //open children
        //append to edgenodes
        //for every edgenode open children and append
        //if goal state exit

        LinkedList<State> edgeNodes = new LinkedList<>();
        edgeNodes.add(initialState);

        while(!edgeNodes.isEmpty()){
            State headState = edgeNodes.removeFirst();
            PrintState(headState.puzzleStructure);
            if(Arrays.equals(headState.puzzleStructure, goalStateStructure)){
                //retrace
                return true;
            }

            edgeNodes.addAll(headState.children());
        }
        return false;



        // Implement depth-first search algorithm here
        // Return the plan as an ArrayList of states
        // Example:
        // plan.add(initialState);
        // while (!goalState.equals(plan.get(plan.size()-1))) {
        //     State currentState = plan.get(plan.size()-1);
        //     ArrayList<State> children = currentState.children();
        //     for (State child : children) {
        //         plan.add(child);
        //     }
        // }
        // return plan;
    }
    
    public static void PrintState(int[] statePuzzlePostion){
        System.out.print("[");        
        for(int i = 0; i < 9; i++){
            if(i == 2 || i == 5)
                System.out.println(statePuzzlePostion[i] + ",");
            else
                System.out.print(statePuzzlePostion[i] + ",");
        }
        System.out.println("]");        
    }
    // Plan finding logic goes here (main)
    // Specific search algorithms go in seprate files
}

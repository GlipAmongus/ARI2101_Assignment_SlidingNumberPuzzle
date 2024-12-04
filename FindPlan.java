import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FindPlan {
    ArrayList<State> plan;
    State goalState = new State(new int[] {1,2,3,4,5,6,7,8,0});

    public void main(String[] args) {

        State initialState1 = new State(new int[] {8,6,7,2,5,4,3,0,1});
        State initialState2 = new State(new int[] {6,4,7,8,5,0,3,2,1});
        goalState.puzzleStructure = new int[] {1,2,3,4,5,6,7,8,0};

        //FindAstarPlan(initialState2);
        if(FindDepthFirstPlan(initialState1))
            System.out.println("Plan found!");
    }

    public boolean FindDepthFirstPlan(State initialState){
        //open children
        //append to edgenodes
        //for every edgenode open children and append
        //if goal state exit
        LinkedList<State> edgeNodes = new LinkedList<>();
        edgeNodes.add(initialState);

        while(!edgeNodes.isEmpty()){
            State headState = edgeNodes.removeFirst();
            edgeNodes.addAll(headState.children());
            
            if(headState == goalState){
                //retrace
                return true;
            }
            edge
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
    

    // Plan finding logic goes here (main)
    // Specific search algorithms go in seprate files
}

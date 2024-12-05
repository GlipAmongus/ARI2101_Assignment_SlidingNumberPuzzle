import java.util.ArrayList;
import java.util.Collections;

public class FindPlan {
    // Plan Output
    public ArrayList<Character> plan;   // list of moves done (l,u,r,d)
    public int actions;                 // length of the plan
    public int uniqueStates;            // number of unique states gen. unexpanded included
    public boolean validity;            // plan validity

    public static void main(String[] args) {

        State initialState = new State(new int[] {6,4,7,8,5,0,3,2,1}, null);

        //FindAstarPlan(initialState2);
        if(SearchAlgorithms.FindBreadthFirstPlan(initialState))
            System.out.println("Plan found!");
        else
        System.out.println("Plan not found!");

    }

    //================= Diagnostic Functions ============================
    public void retracePlanDiagnostics(State goalFound){ // Plan output
        State ancestor = goalFound.parent;
        validity = true;
        while(ancestor != null){            
            plan.add(ancestor.move);  
            actions++;                       
            ancestor = ancestor.parent; 
        }
        Collections.reverse(plan);
    }

    public static void PrintPuzzlePosition(int[] statePuzzlePostion){
        System.out.print("[");        
        for(int i = 0; i < 9; i++){
            switch (i) {
                case 2,5:
                    System.out.println(statePuzzlePostion[i] + ",");
                    break;
                case 8:
                    System.out.print(statePuzzlePostion[i]);
                default:
                    System.out.print(statePuzzlePostion[i] + ",");
                    break;
            }
        }
        System.out.println("]");        
    }
}

PART 1<br /><br />
Navigate to Part1 directory<br />
To compile:<br />
Run: javac -d bin src/*.java <br /><br />
To run:<br />
Run: java -cp bin Main<br /><br />


CONTENTS<br /><br />
State Class<br />
The State class represents a state in the sliding puzzle<br />
board + empty tile index<br />
parent<br />
costs - hcost, gcost, fcost()<br />
children method<br />
swap helper function<br /><br />

Heuristics Class<br />
The Heuristics class contains various 2 heuristic functions<br />
Manhattan Distance<br />
Misplaced Tiles<br /><br />

SearchAlgorithms Class<br />
Breadth-First Search (BFS)<br />
Greedy Search<br />
A Search*<br />
Enforced Hill Climbing (EHC)<br /><br />

Result Class<br />
The Result class stores the outcome of the search<br />
Duration <br />
Actions<br />
Unique States<br />
Plan<br />
Validity<br />
RetracePlan<br />
Validity Step<br /><br />

Main Class<br />
handles user input, displays menu, prints results, optional action printing<br />

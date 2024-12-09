(define (domain TilePuzzleSolver)
    (:requirements :strips :equality)
    (:predicates
        (tile ?tile)        
        (pos ?x ?y)
        (emptyPOS ?x ?y)            
        (incriment ?to ?from)
        (decriment ?to ?from)
    )

    
    (:action moveLEFT
        :parameters (?tile ?x ?y ?xNEW )
        :precondition (and
            (tile ?tile)
            (pos ?x ?y)
            (pos ?xNEW ?y)
            (emptyPOS ?xNEW ?y)
            (decriment ?xNEW ?x)
        )
        :effect (and
            (not (emptyPOS ?xNEW ?y))
            (not (pos ?x ?y))
            (emptyPOS ?x ?y)
            (pos ?xNEW ?y)
        )
    )

    
    (:action moveRIGHT
        :parameters (?tile ?x ?y ?xNEW)
        :precondition (and
            (tile ?tile)
            (pos ?x ?y)
            (pos ?xNEW ?y)
            (emptyPOS ?xNEW ?y)
            (incriment ?xNEW ?x)
        )
        :effect (and
            (not (emptyPOS ?xNEW ?y))
            (not (pos ?x ?y))
            (emptyPOS ?x ?y)
            (pos ?xNEW ?y)
        )
    )

    
    (:action moveDOWN
        :parameters (?tile ?x ?y ?yNEW)
        :precondition (and
            (tile ?tile)
            (pos ?x ?y)
            (pos ?x ?yNEW)
            (emptyPOS ?x ?yNEW)
            (incriment ?yNEW ?y)
        )
        :effect (and
            (not (emptyPOS ?x ?yNEW))
            (not (pos ?x ?y))
            (emptyPOS ?x ?y)
            (pos ?x ?yNEW)
        )
    )

   
    (:action moveUP
        :parameters (?tile ?x ?y ?yNEW)
        :precondition (and
            (tile ?tile)
            (pos ?x ?y)
            (pos ?x ?yNEW)
            (emptyPOS ?x ?yNEW)
            (decriment ?yNEW ?y)
        )
        :effect (and
            (not (emptyPOS ?x ?yNEW))
            (not (pos ?x ?y))
            (emptyPOS ?x ?y)
            (pos ?x ?yNEW)
        )
    )
)

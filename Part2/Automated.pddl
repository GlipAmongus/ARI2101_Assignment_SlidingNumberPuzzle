(define (domain TilePuzzleSolver)
    (:requirements :strips :typing :equality)
    (:types position tile)
    (:predicates
        (tile ?tile - tile)   ;represents a number             
        (pos ?tile - tile ?x - position ?y - position) ;represents position of a number 
        (emptyPOS ?x - position ?y - position)  ;represents position of empty space    
        (increment ?to - position ?from - position)   ;adding +1 to a position
        (decrement ?to - position ?from - position)    ;subtracting -1 from a position
    )

    
    (:action moveLEFT
        :parameters (?tile - tile ?x - position ?y - position ?xNEW - position)
        :precondition (and
            (tile ?tile)    ;validating tile
            (pos ?tile ?x ?y)   ;validating position
            (emptyPOS ?xNEW ?y) ;validating empty position
            (decrement ?xNEW ?x)    ;ensuring xNEW = x - 1
        )
        :effect (and
            (not (emptyPOS ?xNEW ?y))   ;position of emptyPOS has changed
            (not (pos ?tile ?x ?y))     ;position of tile in position x y has changed
            (emptyPOS ?x ?y)        ;subtituting previous emptyPOS of position xNEW y to new position x y
            (pos ?tile ?xNEW ?y)    ;subtituting previous tile of position x y to new position xNEW y
        )
    )

    
    (:action moveRIGHT
        :parameters (?tile - tile ?x - position ?y - position ?xNEW - position)
        :precondition (and
            (tile ?tile)    ;validating tile
            (pos ?tile ?x ?y)   ;validating position
            (emptyPOS ?xNEW ?y) ;validating empty position
            (increment ?xNEW ?x)    ;ensuring xNEW = x + 1
        )
        :effect (and
            (not (emptyPOS ?xNEW ?y))   ;position of emptyPOS has changed
            (not (pos ?tile ?x ?y))     ;position of tile in position x y has changed
            (emptyPOS ?x ?y)        ;subtituting previous emptyPOS of position xNEW y to new position x y
            (pos ?tile ?xNEW ?y)    ;subtituting previous tile of position x y to new position xNEW y
        )
    )

    
    (:action moveDOWN
        :parameters (?tile - tile ?x - position ?y - position ?yNEW - position)
        :precondition (and
            (tile ?tile)    ;validating tile
            (pos ?tile ?x ?y)   ;validating position
            (emptyPOS ?x ?yNEW) ;validating empty position
            (increment ?yNEW ?y)    ;ensuring yNEW = y + 1
        )
        :effect (and
            (not (emptyPOS ?x ?yNEW))   ;position of emptyPOS has changed
            (not (pos ?tile ?x ?y))     ;position of tile in position x y has changed
            (emptyPOS ?x ?y)        ;subtituting previous emptyPOS of position x yNEW to new position x y
            (pos ?tile ?x ?yNEW)    ;subtituting previous tile of position x y to new position x yNEW
        )
    )

    
    (:action moveUP
        :parameters (?tile - tile ?x - position ?y - position ?yNEW - position)
        :precondition (and
            (tile ?tile)    ;validating tile
            (pos ?tile ?x ?y)   ;validating position
            (emptyPOS ?x ?yNEW) ;validating empty position
            (decrement ?yNEW ?y)    ;ensuring yNEW = y - 1
        )
        :effect (and
            (not (emptyPOS ?x ?yNEW))   ;position of emptyPOS has changed
            (not (pos ?tile ?x ?y))     ;position of tile in position x y has changed
            (emptyPOS ?x ?y)        ;subtituting previous emptyPOS of position x yNEW to new position x y
            (pos ?tile ?x ?yNEW)    ;subtituting previous tile of position x y to new position x yNEW
        )
    )
)

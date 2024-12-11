(define (problem problem1)
    (:domain TilePuzzleSolver)
    (:objects 
        p1 p2 p3 - position           
        t1 t2 t3 t4 t5 t6 t7 t8 - tile  
    )

    (:init
        
        (tile t1) (tile t2) (tile t3)
        (tile t4) (tile t5) (tile t6)
        (tile t7) (tile t8)

        
        (increment p2 p1) (increment p3 p2)
        (decrement p1 p2) (decrement p2 p3)

        
        (pos t8 p1 p1) (pos t6 p2 p1) (pos t7 p3 p1)
        (pos t2 p1 p2) (pos t5 p2 p2) (pos t4 p3 p2)
        (pos t3 p1 p3) (emptyPOS p2 p3) (pos t1 p3 p3)

       
        
    )

    (:goal 
        (and
            
            (pos t1 p1 p1) (pos t2 p2 p1) (pos t3 p3 p1)
            (pos t4 p1 p2) (pos t5 p2 p2) (pos t6 p3 p2)
            (pos t7 p1 p3) (pos t8 p2 p3)
            (emptyPOS p3 p3)
        )
    )
)

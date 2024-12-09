(define (problem problem1) (:domain Automated)
(:objects 
    1 2 3 - pos
    t1 t2 t3
    t4 t5 t6
    t7 t8
)

(:init

    (tile t1)(tile t2)(tile t3)
    (tile t4)(tile t5)(tile t6)
    (tile t7)(tile t8)


    (pos 1 1)(pos 2 1)(pos 3 1)
    (pos 1 2)(pos 2 2)(pos 3 2)
    (pos 1 3)(pos 2 3)(pos 3 3)

    (emptyPOS 3 3)

    (incriment 2 1)(incriment 3 2)
    (decriment 1 2)(decriment 2 3)


    (pos t1 1 1)(pos t2 2 1)(pos t3 3 1)
    (pos t4 1 2)(pos t5 2 2)(pos t6 3 2)
    (pos t7 1 3)(pos t8 2 3)(emptyPOS 3 3)


)

(:goal (and
    (pos t1 1 1)(pos t2 2 1)(pos t3 3 1)
    (pos t4 1 2)(pos t5 2 2)(pos t6 3 2)
    (pos t7 1 3)(pos t8 2 3)(emptyPOS 3 3)
))


)

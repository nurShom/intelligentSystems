(deffacts case-2
    (block a)
    (block b)
    (block c)
    (block d)
    (block e)
    
    (on-top-of (top nothing) (bottom b))
    (on-top-of (top b) (bottom a))
    (on-top-of (top a) (bottom floor))
    
    (on-top-of (top nothing) (bottom c))
    (on-top-of (top c) (bottom floor))
    
    (on-top-of (top nothing) (bottom e))
    (on-top-of (top e) (bottom d))
    (on-top-of (top d) (bottom floor))
    
    (goal (move b) (on-top-of a) (state unsatisfied))
    (goal (move a) (on-top-of c) (state unsatisfied))
    ;(goal (move c) (on-top-of floor) (state unsatisfied))
        
    )
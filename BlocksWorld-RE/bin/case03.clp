(deffacts case-3
    (block A)
    (block B)
    (block C)
    
    (on-top-of (top nothing) (bottom B))
    (on-top-of (top B) (bottom floor))
    
    (on-top-of (top nothing) (bottom C))
    (on-top-of (top C) (bottom A))
    (on-top-of (top A) (bottom floor))
    
    (goal (move C) (on-top-of floor) (state unsatisfied) (step 0))
	(goal (move B) (on-top-of C) (state new) (step 1))
    (goal (move A) (on-top-of B) (state new) (step 2))
    
    )


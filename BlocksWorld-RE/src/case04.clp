(deffacts case-4
    (block X)
    (block B)
    (block E)
    (block A)
    (block T)
    (block L)
    (block Q)
    (block D)
    (block R)
    (block J)
    
    (on-top-of (top nothing) (bottom X))
    (on-top-of (top X) (bottom floor))
    
    (on-top-of (top nothing) (bottom B))
    (on-top-of (top B) (bottom E))
    (on-top-of (top E) (bottom A))
    (on-top-of (top A) (bottom T))
    (on-top-of (top T) (bottom floor))
    
    (on-top-of (top nothing) (bottom L))
    (on-top-of (top L) (bottom Q))
    (on-top-of (top Q) (bottom floor))
    
    (on-top-of (top nothing) (bottom D))
    (on-top-of (top D) (bottom R))
    (on-top-of (top R) (bottom floor))
    
    (on-top-of (top nothing) (bottom J))
    (on-top-of (top J) (bottom floor))

    (goal (move E) (on-top-of floor) (state unsatisfied) (step 0))
    (goal (move L) (on-top-of E) (state new) (step 1))
    (goal (move B) (on-top-of L) (state new) (step 2))
    (goal (move A) (on-top-of B) (state new) (step 3))
    (goal (move T) (on-top-of A) (state new) (step 4))
    
    )

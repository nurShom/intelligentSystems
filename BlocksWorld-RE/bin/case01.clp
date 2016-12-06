(deffacts case-1
    
    (block A)
    (block B)
    (block C)
    (block D)
    (block E)
    (block F)
    
    (on-top-of (top nothing) (bottom A))
    (on-top-of (top A) (bottom B))
    (on-top-of (top B) (bottom C))
    (on-top-of (top C) (bottom floor))
    
    (on-top-of (top nothing) (bottom D)) ;nil
    (on-top-of (top D) (bottom E))
    (on-top-of (top E) (bottom F))
    (on-top-of (top F) (bottom floor))
    
    (goal (move C) (on-top-of E) (state unsatisfied) (step 0))

    )
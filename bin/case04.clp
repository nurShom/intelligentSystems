(deffacts case-4
    (block X)
    (block B)
    (block E)
    (block A)
    (block T)
    (block L)
    (block Q)
    (block B)
    (block R)
    (block J)
    
    (on-top-of (top nothing) (bottom A))
    (on-top-of (top A) (bottom floor))
    
    (on-top-of (top nothing) (bottom B))
    (on-top-of (top B) (bottom E))
    (on-top-of (top E) (bottom A))
    (on-top-of (top A) (bottom T))
    (on-top-of (top T) (bottom floor))
    
    (on-top-of (top nothing) (bottom L))
    (on-top-of (top L) (bottom Q))
    (on-top-of (top Q) (bottom floor))
    
    (on-top-of (top nothing) (bottom B))
    (on-top-of (top B) (bottom R))
    (on-top-of (top R) (bottom floor))
    
    (on-top-of (top nothing) (bottom J))
    (on-top-of (top J) (bottom floor))
    
    (goal (move T) (on-top-of A))
    ;(goal (move A) (on-top-of floor))
    ;(goal (move E) (on-top-of floor))
    ;(goal (move T) (on-top-of A))
    ;(goal (move A) (on-top-of B))
    ;(goal (move B) (on-top-of L))
    ;(goal (move L) (on-top-of E))
    ;(goal (move E) (on-top-of floor))
    
    )

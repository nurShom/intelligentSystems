;;;;;;;;;Templates
(deftemplate on-top-of
    (slot top)
    (slot bottom)
    )

;(deftemplate build
;    (slot block))

(deftemplate goal
    (slot move)
    (slot on-top-of))

;;;;;;;;;;Initial Facts

/*
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
    
    (goal (move C) (on-top-of E))

    )
*/


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


;;;;;;;;;Rules
(defrule move-directly
    ?goal <- (goal (move ?tp) (on-top-of ?bm))
    (block ?tp)
    (block ?bm)
    (on-top-of (top nothing) (bottom ?tp))
    ?s1 <- (on-top-of (top ?tp) (bottom ?other))
    ?s2 <- (on-top-of (top nothing) (bottom ?bm))
    =>
    (retract ?goal ?s1 ?s2)
    (assert (on-top-of (top ?tp) (bottom ?bm)))
    (assert (on-top-of (top nothing) (bottom ?other)))
    (printout t ?tp " moved on top of " ?bm "." crlf)
    )

(defrule move-to-floor
    ?goal <- (goal (move ?tp) (on-top-of floor))
    (block ?tp)
    (on-top-of (top nothing) (bottom ?tp))
    ?s <- (on-top-of (top ?tp) (bottom ?other))
    =>
    (retract ?goal ?s)
    (assert (on-top-of (top ?tp) (bottom floor))
        	(on-top-of (top nothing) (bottom ?other)))
    (printout t ?tp " moved to " floor "." crlf)
    
    )

(defrule clear-upper-block
    (goal (move ?tp))
    (block ?tp)
    (on-top-of (top ?other) (bottom ?tp))
    (block ?other)
    =>
    (assert (goal (move ?other) (on-top-of floor)))
    )

(defrule clear-lower-block
    (goal (on-top-of ?bt))
    (block ?bt)
    (on-top-of (top ?other) (bottom ?bt))
    (block ?other)
    =>
    (assert (goal (move ?other) (on-top-of floor)))
    )

(reset)

;(batch "blocks.clp")
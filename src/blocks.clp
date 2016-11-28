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
(deffacts initial-state
    
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
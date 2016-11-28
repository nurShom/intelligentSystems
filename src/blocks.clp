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
;(batch "case01.clp")
;(batch "case02.clp")
;(batch "case03.clp")
(batch "case04.clp")



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
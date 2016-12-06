;;;;;;;;;Templates
(deftemplate on-top-of
    (slot top)
    (slot bottom)
    )

(deftemplate goal
    (slot move)
    (slot on-top-of)
    (slot state)
    (slot step 
        (type INTEGER))
    )

(deftemplate step
    (slot count
        (type INTEGER))
    )

;;;;;;;;;;Initial Facts
;(batch "case01.clp")
;(batch "case02.clp")
;(batch "case03.clp")
(batch "case04.clp")

(deffacts step-0
    (step (count 0))
    )

;;;;;;;;;Rules

(defrule enable-next-goal
    (goal (state satisfied) (step ?s))
	?step <- (step (count ?s))
    ?goal <- (goal (state new) (step ?n&:(= ?n (+ ?s 1)) ))
    =>
    (bind ?next (+ ?s 1))
    (modify ?step (count ?next))
    (modify ?goal (state unsatisfied))
    )


(defrule move-directly
    ?goal <- (goal (move ?tp) (on-top-of ?bm) (state unsatisfied))
    (block ?tp)
    (block ?bm)
    (on-top-of (top nothing) (bottom ?tp))
    ?s1 <- (on-top-of (top ?tp) (bottom ?other))
    ?s2 <- (on-top-of (top nothing) (bottom ?bm))
    =>
    (retract ?s1 ?s2)
    (modify ?goal (state satisfied))
    (assert (on-top-of (top ?tp) (bottom ?bm)))
    (assert (on-top-of (top nothing) (bottom ?other)))
    (printout t ?tp " moved on top of " ?bm "." crlf)
    )

(defrule move-to-floor
    ?goal <- (goal (move ?tp) (on-top-of floor) (state unsatisfied))
    (block ?tp)
    (on-top-of (top nothing) (bottom ?tp))
    ?s <- (on-top-of (top ?tp) (bottom ?other))
    =>
    (retract ?s)
    (modify ?goal (state satisfied))
    (assert (on-top-of (top ?tp) (bottom floor))
        	(on-top-of (top nothing) (bottom ?other)))
    (printout t ?tp " moved to " floor "." crlf)
    
    )

(defrule clear-upper-block
    (goal (move ?tp) (state unsatisfied))
    (block ?tp)
    (on-top-of (top ?other) (bottom ?tp))
    (block ?other)
    =>
    (assert (goal (move ?other) (on-top-of floor) (state unsatisfied)))
    (printout t "New sub-goal: move " ?other " to " floor "." crlf)
    )

(defrule clear-lower-block
    (goal (on-top-of ?bt) (state unsatisfied))
    (block ?bt)
    (on-top-of (top ?other) (bottom ?bt))
    (block ?other)
    =>
    (assert (goal (move ?other) (on-top-of floor) (state unsatisfied)))
    (printout t "New sub-goal: move " ?other " to " floor "." crlf)
    )

(defrule clear-null-fact
    ?f <- (on-top-of (top nothing) (bottom floor))
    =>
    (retract ?f)
    (printout t "deleted empty fact: (top nothing) (bottom floor)." crlf)
    )

(reset)

;(batch "blocks.clp")
(* TD - S\u00e9mantique \u00e0 petit pas des expressions artihm\u00e9tiques  *)
(*   Pascal Fradet, Maxime Lesourd, Jean-Francois Monin       *)
(*              UGA Polytech RICM4                            *)

Require Import Nat List Aexp_td.

Import List.ListNotations.

(*  ------------------------------------------------------------     *)
(*     S\u00e9mantique \u00e0 petits pas des expressions arithm\u00e9tiques         *)
(* aka S\u00e9mantique Op\u00e9rationnelle Structur\u00e9e (SOS)                    *)

(* Cette s\u00e9mantique d\u00e9crit chaque pas de r\u00e9duction possible (step).
   Par exemple la r\u00e8gle 
    SOSA_Step_pl_l : forall a1 a1' a2,
                     SOSA_Step s a1 a1' -> SOSA_Step s (Apl a1 a2) (Apl a1' a2)
   peut se lire "si a1 se reduit en une \u00e9tape en a1' alors Apl a1 a2
   se r\u00e9duit en une \u00e9tape en Apl a1' a2"

   On peut voir la s\u00e9mantique d'une expression comme l'entier en
   lequel elle peut \u00eatre r\u00e9duite par la fermeture transitive de step
*)
(*                  Un pas de r\u00e9duction                         *)

Inductive SOSA_Step (s : Etat) : Aexp -> Aexp -> Prop :=
| SOSA_Step_va : forall x, SOSA_Step s (Ava x) (Anu (Nu (s x)))

| SOSA_Step_pl_l : forall a1 a1' a2,
                   SOSA_Step s a1 a1' -> SOSA_Step s (Apl a1 a2) (Apl a1' a2)
| SOSA_Step_pl_r : forall a1 a2 a2',
                   SOSA_Step s a2 a2' -> SOSA_Step s (Apl a1 a2) (Apl a1 a2')
| SOSA_Step_pl_v : forall n1 n2,
                   SOSA_Step s (Apl (Anu (Nu n1)) (Anu (Nu n2)))  (Anu (Nu (n1 + n2)))

| SOSA_Step_mu_l : forall a1 a1' a2,
                   SOSA_Step s a1 a1' -> SOSA_Step s (Amu a1 a2) (Amu a1' a2)
| SOSA_Step_mu_r : forall a1 a2 a2',
                   SOSA_Step s a2 a2' -> SOSA_Step s (Amu a1 a2) (Amu a1 a2')
| SOSA_Step_mu_v : forall n1 n2,
                   SOSA_Step s (Amu (Anu (Nu n1)) (Anu (Nu n2))) (Anu (Nu (n1 * n2)))

| SOSA_Step_mo_l : forall a1 a1' a2,
                   SOSA_Step s a1 a1' -> SOSA_Step s (Amo a1 a2) (Amo a1' a2)
| SOSA_Step_mo_r : forall a1 a2 a2',
                   SOSA_Step s a2 a2' -> SOSA_Step s (Amo a1 a2) (Amo a1 a2')
| SOSA_Step_mo_v : forall n1 n2,
                   SOSA_Step s (Amo (Anu (Nu n1)) (Anu (Nu n2))) (Anu (Nu (n1 - n2))).

(* Fermeture reflexive transitive de step *)

Inductive SOS_A (s : Etat) : Aexp -> Aexp -> Prop :=
| SOSA_base : forall a, SOS_A s a a
| SOSA_step : forall a a' a'', SOSA_Step s a a' -> SOS_A s a' a'' -> SOS_A s a a''.


(* On montre la transitivit\u00e9 de la relation step* (SOS_A)     *)
Lemma SOSA_trans : forall s a1 a2 a3, SOS_A s a1 a2 -> SOS_A s a2 a3 -> SOS_A s a1 a3.
Proof.
             (* A COMPLETER *)
intros s a1 a2 a3.
intros h1.
intros h2.
induction h1.
- apply h2.
- apply SOSA_step with (a':= a').
 apply H.
 apply IHh1.
 apply h2.

Qed.

(* Les trois lemmes suivants montrent que les les op\u00e9rations binaires
   sont en relation (i.e. peuvent \u00eatre r\u00e9duites par step^* ) si leurs
   op\u00e9randes sont en relation.
*)

Lemma SOSA_pl : forall s a1 a1' a2 a2', SOS_A s a1 a1' -> SOS_A s a2 a2'
                                     -> SOS_A s (Apl a1 a2) (Apl a1' a2').
Proof.
intros s a1 a1' a2 a2' H1 H2.
induction H1 as [a1 | a11 a12 a13]; induction H2 as [a2 | a21 a22 a23].
- constructor.
- apply SOSA_step with (a':=Apl a1 a22); try assumption.
  apply SOSA_Step_pl_r.
  assumption.
- apply SOSA_step with (a':=Apl a12 a2); try assumption.
  apply SOSA_Step_pl_l.
  assumption.
- apply SOSA_step with (a':=Apl a12 a21); try assumption.
  apply SOSA_Step_pl_l.
  assumption.
Qed.

Lemma SOSA_mo : forall s a1 a1' a2 a2', SOS_A s a1 a1' -> SOS_A s a2 a2'
                                     -> SOS_A s (Amo a1 a2) (Amo a1' a2').
Proof.
intros s a1 a1' a2 a2' H1 H2.
induction H1 as [a1 | a11 a12 a13]; induction H2 as [a2 | a21 a22 a23].
- constructor.
- apply SOSA_step with (a':=Amo a1 a22); try assumption.
  apply SOSA_Step_mo_r.
  assumption.
- apply SOSA_step with (a':=Amo a12 a2); try assumption.
  apply SOSA_Step_mo_l.
  assumption.
- apply SOSA_step with (a':=Amo a12 a21); try assumption.
  apply SOSA_Step_mo_l.
  assumption.
Qed.

Lemma SOSA_mu : forall s a1 a1' a2 a2', SOS_A s a1 a1' -> SOS_A s a2 a2'
                                     -> SOS_A s (Amu a1 a2) (Amu a1' a2').
Proof. 
             (* A COMPLETER *)
Admitted.


(* On peut montrer l'\u00e9quivalence de la s\u00e9mantique SOS et de la s\u00e9mantique fonctionnelle *)

(*              SOS => SemA              *)

(* Une expression \u00e0 la m\u00eame s\u00e9mantique d\u00e9notationnelle apr\u00e8s une \u00e9tape de r\u00e9duction (step) *)

Lemma SOSA_Step_imp_SemA : forall s a a', SOSA_Step s a a' -> semA a s = semA a' s.
Proof.
intros s a a' H.
induction H as [ ? | ? ? ? ? IH | ? ? ? ? IH  | ? | ? ? ? ? IH |? ? ? ? IH
               | ? | ? ? ? ? IH | ? ? ? ? IH | ?]; cbn;
               try (rewrite IH); reflexivity.
Qed.


(* Une expression a la m\u00eame s\u00e9mantique d\u00e9notationnelle apr\u00e8s un nombre arbitraire
   d'\u00e9tapes de r\u00e9duction.
*)
Theorem SOSA_imp_SemA_gen : forall s a a', SOS_A s a a' -> semA a s = semA a' s.
Proof. 
             (* A COMPLETER *)
Admitted.

(* En particulier une expression se r\u00e9duit (par SOS_A) dans le m\u00eame entier
   que celui sp\u00e9cifi\u00e9 par la semantique d\u00e9notationnelle (semA)
*)

Corollary SOSA_imp_SemA : forall s a n,  SOS_A s a (Anu (Nu n)) -> semA a s = n.
Proof.
             (* A COMPLETER *)
Admitted.


(*                   SemA => SOS_A
  Si la s\u00e9mantique d\u00e9notationnelle d'une expression a est n
  alors a se r\u00e9duit (par SOS_A) en Anu (Nu n)
*)
Theorem SemA_imp_SOSA : forall s a, SOS_A s a (Anu (Nu (semA a s))).
Proof.
intros s a.
induction a as [n | x | a1 IH1 a2 IH2 | a1 IH1 a2 IH2 | a1 IH1 a2 IH2];
simpl.
- destruct n. simpl. apply SOSA_base.
- eapply SOSA_step.
  + apply SOSA_Step_va.
  + apply SOSA_base.
- assert (G: SOS_A s (Apl a1 a2) (Apl (Anu (Nu (semA a1 s))) (Anu (Nu (semA a2 s))))).
  + apply SOSA_pl; assumption.

             (* A COMPLETER *)
  + admit.
- assert (G: SOS_A s (Amu a1 a2) (Amu (Anu (Nu (semA a1 s))) (Anu (Nu (semA a2 s))))).
              (* A COMPLETER *)
admit. admit.

-               (* A COMPLETER *)
admit.
Admitted.

(* En utilisant les r\u00e9sultats de correction pr\u00e9c\u00e9dents, on peut montrer
   facilement l'\u00e9quivalence de la s\u00e9mantique naturelle et de la SOS
*)

Lemma NSA_equiv_SOSA : forall s a n, NSA s a n <-> SOS_A s a (Anu (Nu n)).
Proof.
              (* A COMPLETER *)
Admitted.

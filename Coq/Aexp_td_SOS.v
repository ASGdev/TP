(* TD - Sémantique à petit pas des expressions artihmétiques  *)
(*   Pascal Fradet, Maxime Lesourd, Jean-Francois Monin       *)
(*              UGA Polytech RICM4                            *)

Require Import Nat List Aexp_td.

Import List.ListNotations.

(*  ------------------------------------------------------------     *)
(*     Sémantique à petits pas des expressions arithmétiques         *)
(* aka Sémantique Opérationnelle Structurée (SOS)                    *)

(* Cette sémantique décrit chaque pas de réduction possible (step).
   Par exemple la règle 
    SOSA_Step_pl_l : forall a1 a1' a2,
                     SOSA_Step s a1 a1' -> SOSA_Step s (Apl a1 a2) (Apl a1' a2)
   peut se lire "si a1 se reduit en une étape en a1' alors Apl a1 a2
   se réduit en une étape en Apl a1' a2"

   On peut voir la sémantique d'une expression comme l'entier en
   lequel elle peut être réduite par la fermeture transitive de step
*)
(*                  Un pas de réduction                         *)

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


(* On montre la transitivité de la relation step* (SOS_A)     *)
Lemma SOSA_trans : forall s a1 a2 a3, SOS_A s a1 a2 -> SOS_A s a2 a3 -> SOS_A s a1 a3.
Proof.
  intro s a1.
  simpl.
(* Les trois lemmes suivants montrent que les les opérations binaires
   sont en relation (i.e. peuvent être réduites par step^* ) si leurs
   opérandes sont en relation.
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


(* On peut montrer l'équivalence de la sémantique SOS et de la sémantique fonctionnelle *)

(*              SOS => SemA              *)

(* Une expression à la même sémantique dénotationnelle après une étape de réduction (step) *)

Lemma SOSA_Step_imp_SemA : forall s a a', SOSA_Step s a a' -> semA a s = semA a' s.
Proof.
intros s a a' H.
induction H as [ ? | ? ? ? ? IH | ? ? ? ? IH  | ? | ? ? ? ? IH |? ? ? ? IH
               | ? | ? ? ? ? IH | ? ? ? ? IH | ?]; cbn;
               try (rewrite IH); reflexivity.
Qed.


(* Une expression a la même sémantique dénotationnelle après un nombre arbitraire
   d'étapes de réduction.
*)
Theorem SOSA_imp_SemA_gen : forall s a a', SOS_A s a a' -> semA a s = semA a' s.
Proof. 
             (* A COMPLETER *)
Admitted.

(* En particulier une expression se réduit (par SOS_A) dans le même entier
   que celui spécifié par la semantique dénotationnelle (semA)
*)

Corollary SOSA_imp_SemA : forall s a n,  SOS_A s a (Anu (Nu n)) -> semA a s = n.
Proof.
             (* A COMPLETER *)
Admitted.


(*                   SemA => SOS_A
  Si la sémantique dénotationnelle d'une expression a est n
  alors a se réduit (par SOS_A) en Anu (Nu n)
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

(* En utilisant les résultats de correction précédents, on peut montrer
   facilement l'équivalence de la sémantique naturelle et de la SOS
*)

Lemma NSA_equiv_SOSA : forall s a n, NSA s a n <-> SOS_A s a (Anu (Nu n)).
Proof.
              (* A COMPLETER *)
Admitted.

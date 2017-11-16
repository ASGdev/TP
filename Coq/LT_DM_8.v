(*  Langage et traducteurs - Contrôle continu / DM  - RICM4 - 2017

Ce fichier Coq n'importe pas les fichiers de TD précédents.
Les exercices portent sur une variante un peu plus simple
des expressions Aexp étudiées en TD: la seule différence est
que l'entier n est représenté par Anu n alors que dans les
TDs il est représenté par Anu (Nu n). Les preuves s'en trouvent
simplifiées.


Si vous êtes bloqués dans un lemme intermédiaire
il est mieux d'avancer en le supposant montré (par Admitted) plutôt
que rester bloqué. Par exemple, il est mieux de traiter la Question 2
en supposant la Question 1 résolue que de ne rien faire du tout.


A FAIRE: On complétera au mieux les parties notées (* A compléter *).
         Le fichier .v complété devra être envoyé par courriel à 
         pascal.fradet@inria.fr, maxime.lesourd.inria.fr, et
         jean-francois.monin@univ-grenoble-alpes.fr
         le  mardi 28 novembre 2017 au plus tard.
*)

Require Import Arith.


(* *************************************************************************** *)
(*  LES EXPRESSIONS ET LEUR SEMANTIQUE                                         *)
(* *************************************************************************** *)

Inductive Var : Set := Va : nat -> Var.

(* Expressions arithmétiques avec constantes, variables et operations de base *)
Inductive Aexp : Set :=
| Anu : forall (n:nat), Aexp     (* Constante *)
| Ava : forall (x:Var), Aexp     (* Variable *)
| Apl : forall a1 a2: Aexp, Aexp (* Addition *)
| Amu : forall a1 a2: Aexp, Aexp (* Multiplication *)
| Amo : forall a1 a2: Aexp, Aexp (* Soustraction *).

(* ------------------------------------------------------------ *)
(*                     Etats                                    *)

Definition Etat := Var -> nat.

(* ------------------------------------------------------------ *)
(*   Sémantique dénotationnelle des expressions arithmétiques   *)

Fixpoint semA (a: Aexp) (s: Etat) : nat :=
  match a with
    | Anu n => n
    | Ava x => s x
    | Apl a1 a2 => semA a1 s + semA a2 s (* Complétez ici *)
    | Amu a1 a2 => semA a1 s * semA a2 s (* Complétez ici *)
    | Amo a1 a2 => semA a1 s - semA a2 s (* Complétez ici *)
  end.


(* *************************************************************************** *)
(*  QUESTION 1          Montrer en Coq la propriété suivante par induction     *)
(*                    (l'utilisation de lemmes de librairie est interdite)     *)
(* *************************************************************************** *)

Lemma dm_exp_nat_eq : forall n, n - n = 0.
Proof.
      intro n. induction n;simpl.
        - reflexivity.
        - apply IHn. 
Qed.



(* *************************************************************************** *)
(*  QUESTION 2         Montrer en Coq que les expressions                      *)
(*         		         Amo a a et Anu 0                                        *)
(*                     ont la même sémantique dénotationnelle.                 *)
(* *************************************************************************** *)


Lemma dm_eq_DS : forall n s, semA (Amo n n) s = semA (Anu 0) s.
Proof.
  intros n s. induction n;apply dm_exp_nat_eq. 
Qed.

(********************************************************************************)
(*  QUESTION 3.   On donne les définitions de fonctions suivantes               *)
(* **************************************************************************** *)

Definition red_pl a1 a2 :=
  match a1, a2 with
  | Anu n1, Anu n2 => Anu (n1 + n2)
  | b1, b2 => Apl b1 b2
  end.

Definition red_mu a1 a2 :=
  match a1, a2 with
  | Anu n1, Anu n2 => Anu (n1 * n2)
  | b1, b2 => Amu b1 b2
  end.

Definition red_mo a1 a2 :=
  match a1, a2 with
  | Anu n1, Anu n2 => Anu (n1 - n2)
  | b1, b2 => Amo b1 b2
  end.

Fixpoint Red (a : Aexp) : Aexp :=
    match a with
    | Apl a1 a2 => red_pl (Red a1) (Red a2) 
    | Amu a1 a2 => red_mu (Red a1) (Red a2) 
    | Amo a1 a2 => red_mo (Red a1) (Red a2) 
    | a => a
    end.
(* a -  Décrire succintement l'effet de Red sur les expressions Aexp           *)
           
(*      Red décompose                                               *)


(* b - Montrer la correction de la fonction Red                                *)
(*     Il est conseillé d'introduire et de montrer des lemmes intermédiaires   *)
(*     sur red_pl, red_mu, red_mo.                                             *)


Theorem Red_correct : forall a s, semA (Red a) s = semA a s.
Proof. 
  Lemma red_pl_correct : forall a1 a2 s, semA(red_pl a1 a2) s = semA (Apl a1 a2) s.
  Proof.
    intros a1 a2 s. induction red_pl;simpl.
    - induction semA;simpl. 
  Qed.
  intros a s.
Admitted.

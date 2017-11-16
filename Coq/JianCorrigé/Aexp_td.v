(* Formalisation du langage WHILE du livre de Nielson & Nielson *)
(* Jean-Francois Monin, Pascal Fradet, Maxime Lesourd           *)
(* UGA Polytech RICM                                            *)

(* S\u00c3\u00a9mantique des expressions arithm\u00c3\u00a9tiques *)

Require Import Nat.

(* Deux types distincts pour \u00c3\u00a9viter de risquer d'additionner des
   Num et des Var *)
Inductive Num : Set := Nu : nat -> Num.
Inductive Var : Set := Va : nat -> Var.

(* Expressions arithm\u00c3\u00a9tiques avec constantes, variables et operations de base *)
Inductive Aexp : Set :=
| Anu : forall (n:Num), Aexp     (* Constante *)
| Ava : forall (x:Var), Aexp     (* Variable *)
| Apl : forall a1 a2: Aexp, Aexp (* Addition *)
| Amu : forall a1 a2: Aexp, Aexp (* Multiplication *)
| Amo : forall a1 a2: Aexp, Aexp (* Soustraction *)
.

(* Raccourcis pour les exemples *)
Definition x:= Va 0.
Definition y:= Va 1.

(* Exercice : Definissez l'expression qui repr\u00c3\u00a9sente x^2 - 2 *)
Definition a1 := Amo(Amu (Ava x) (Ava x)) (Anu (Nu 2)).

(* ------------------------------------------------------------ *)
(* Etats *)

(* Un \u00c3\u00a9tat est une fonction qui assigne une valeur \u00c3  chaque variable *)
Definition Etat := Var -> nat.

(* Etat de base, toutes les variables ont pour valeur 0 *)
Definition e_init : Etat :=
  fun v => 0.

(* Egalit\u00c3\u00a9 sur les variables *)
Definition eqvar : Var -> Var -> bool :=
  fun x y =>
    match x, y with
      Va u, Va v => eqb u v
    end.

(* masque x n e est l'etat e sauf pour x qui vaut v *)
Definition masque (x: Var) (n: nat) (e: Etat) : Etat :=
  fun y => if eqvar x y then n else e y.

(* Permet de definir un etat o\u00c3\u00b9 x vaut n *)
Definition init_x n := masque (Va 0) n e_init.

(* ------------------------------------------------------------ *)
(* S\u00c3\u00a9mantique d\u00c3\u00a9notationnelle des expressions arithm\u00c3\u00a9tiques *)

(* Il faut retirer le constructeur d'un num pour obtenir un entier *)
Definition semN (n:Num) : nat := match n with Nu i => i end.

(* Evaluation des expressions arithm\u00c3\u00a9tiques *)
Fixpoint semA (a: Aexp) (s: Etat) : nat :=
  match a with
    | Anu n => semN n
    | Ava x => s x
    | Apl a1 a2 => (semA a1 s) + (semA a2 s)(* Compl\u00c3\u00a9tez ici *)
    | Amu a1 a2 => (semA a1 s) * (semA a2 s)(* Compl\u00c3\u00a9tez ici *)
    | Amo a1 a2 => (semA a1 s) - (semA a2 s)(* Compl\u00c3\u00a9tez ici *)
  end.

Example a1_2 : semA a1 (init_x 2) = 2.
simpl. reflexivity.
Qed.

Example a1_3 : semA a1 (init_x 3) = 7.
simpl. reflexivity.
Qed.

(* ------------------------------------------------------------ *)
(* S\u00c3\u00a9mantique \u00c3  grands pas des expressions arithm\u00c3\u00a9tiques *)

(* Relation d'evaluation \u00c3  grands pas pour Aexp *)
(* NSA s a n signifie que dans l'etat s, a s'\u00c3\u00a9value en n *)
Inductive NSA (s : Etat) : Aexp -> nat -> Prop :=
| NSAnu : forall n, NSA s (Anu n) (semN n)

| NSAva : forall x, NSA s (Ava x) (s x)

| NSApl : forall a1 n1 a2 n2, NSA s a1 n1 ->
                              NSA s a2 n2 ->
                              NSA s (Apl a1 a2) (n1 + n2)

| NSAmu : forall a1 n1 a2 n2, NSA s a1 n1 ->
                              NSA s a2 n2 ->
                              NSA s (Amu a1 a2) (n1 * n2)

| NSAmo : forall a1 n1 a2 n2, NSA s a1 n1 ->
                              NSA s a2 n2 ->
                              NSA s (Amo a1 a2) (n1 - n2).

(* Example : 2 + 2 = 4 *)
(* Note : la tactique 'replace x with (t) by reflexivity' permet de
   remplacer un terme x par un terme t qui lui est \u00c3\u00a9gal. *)

Example NS_test : forall s, NSA s (Apl (Anu (Nu 2)) (Anu (Nu 2))) 4.
intros s.
replace 4 with (2 + 2) by reflexivity.
apply NSApl.
- apply NSAnu.
- apply NSAnu.
Qed.

(* Exercice : Prouvez que a1 s'\u00c3\u00a9value en 7 quand x vaut 3 *)
Example NS_a1_3 : forall s, s x = 3 -> NSA s a1 7.
(* Compl\u00c3\u00a9tez ici *)
intros s.
intros H.
replace 7 with (3*3-2) by reflexivity.
unfold a1.
apply NSAmo.
-apply NSAmu.
rewrite <-H.
apply NSAva.
rewrite <-H.
apply NSAva.
-apply NSAnu.

Qed.

(* Exercice : Prouvez les th\u00c3\u00a9oremes NSA_semA et semA_NSA
   Ces th\u00c3\u00a9oremes expriment la compatibilit\u00c3\u00a9 des s\u00c3\u00a9mantiques *)
Theorem NSA_semA : forall a s, NSA s a (semA a s).
Proof.
  (* Compl\u00c3\u00a9tez ici *)
intros  a s.
induction a; simpl.
apply NSAnu.
apply NSAva.
-apply NSApl.
apply IHa1.
apply IHa2.
-apply NSAmu.
apply IHa1.
apply IHa2.
-apply NSAmo.
apply IHa1.
apply IHa2.

Qed.

Theorem semA_NSA : forall a s n, NSA s a n -> n = semA a s.
Proof.
  (* Compl\u00c3\u00a9tez ici *)
 intros a s n.
 intros H.
 induction H;simpl.
 -reflexivity.
  -reflexivity.
-rewrite ->IHNSA1.
 rewrite ->IHNSA2.
  reflexivity.
-rewrite ->IHNSA1.
 rewrite ->IHNSA2.
  reflexivity.
-rewrite ->IHNSA1.
 rewrite ->IHNSA2.
 reflexivity.

 
  
Qed.
 (*Exercice (Optionel) : Prouvez en utilisant les r\u00c3\u00a9sultats pr\u00c3\u00a9c\u00c3\u00a9dents
   que la s\u00c3\u00a9mantique \u00c3  grands pas est deterministe *)


Corollary NSA_det : forall a s n n', NSA s a n -> NSA s a n' -> n = n'.
Proof.
  (* Compl\u00c3\u00a9tez ici *)
Qed.
(* ------------------------------------------------------------ *)
(* Optimisation *)

(* Il est possible de d\u00c3\u00a9crire des transformations sur les expressions
   et de prouver leur correction en Coq *)
(* Cette optimisation simplifie les sous termes de la forme
   0 + a *)
Fixpoint elim_plus_0 (a : Aexp) : Aexp :=
  match a with
  | Anu n => Anu n
  | Ava x => Ava x
  | Apl (Anu (Nu 0)) a2 =>
    elim_plus_0 a2
  | Apl a1 a2 => Apl (elim_plus_0 a1) (elim_plus_0 a2)
  | Amu a1 a2 => Amu (elim_plus_0 a1) (elim_plus_0 a2)
  | Amo a1 a2 => Amo (elim_plus_0 a1) (elim_plus_0 a2)
  end.

Definition a2 := Apl (Anu (Nu 0)) (Ava x).
Example test_elim_plus_0 : elim_plus_0 a2 = Ava x.
simpl. reflexivity.
Qed.

(* Exercice : Prouvez la correction de cette optimisation \u00c3  l'aide du lemme semA_pl *)

(* Note : La commande 'tactique1; try tactique2' permet d'\u00c3\u00a9liminer les buts
   g\u00c3\u00a9n\u00c3\u00a9r\u00c3\u00a9s par tactique1 en utilisant tactique2
   Par exemple 'try reflexivity' \u00c3\u00a9limine les cas o\u00c3\u00b9 reflexivity suffit *)
(* Note : pour raisonner par cas sur un Num il faut utiliser 'destruct' 2 fois
   - la premi\u00c3\u00a8re fois pour exposer le constructeur Nu
   - la seconde pour distinguer le cas 0 du cas S n *)

Lemma semA_pl : forall a1 a2 s,
    semA (elim_plus_0 (Apl a1 a2)) s = semA (elim_plus_0 a1) s + semA (elim_plus_0 a2) s.
Proof.
  (* Compl\u00c3\u00a9tez ici *)
Qed.

Lemma elim_plus_0_correct : forall a s, semA (elim_plus_0 a) s = semA a s.
Proof.
  (* Compl\u00c3\u00a9tez ici *)
Qed.


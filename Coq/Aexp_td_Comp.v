(* Version enseignante - avec solutions                     *)
(* TD - Compilation certifiée des expressions artihmétiques *)
(* Pascal Fradet, Maxime Lesourd, Jean-Francois Monin       *)
(*            UGA Polytech RICM4                            *)

Require Import Nat List Aexp_td_sol Aexp_td_SOS.

Import List.ListNotations.

(* ----------------   Compilation de Aexp   ----------------------- *)
(* On va maintenant compiler le langage Aexp vers un langage d'assemblage
   à pile et montrer la correction du compilateur.
   Nous définissons : - le langage d'assemblage (Code)
                      - sa sémantique (exec)
                      - le compilateur (compileA)
 *)

(* Langage d'assemblage à pile (LAP) *)

Inductive Instr : Set :=
  | IpushC : forall n: nat, Instr
  | Ifetch : forall x:Var, Instr
  | Iadd :  Instr
  | Isub :  Instr
  | Imul :  Instr.

(* Un code est une liste d'instruction *)

Definition Code := list Instr.

(* Sémantique fonctionelle (dénotationnelle) du langage  *)

(* La pile de valeurs sur lesquelles travaille le jeu d'instructions
   est représentée par une liste.
   Empiler 2 puis empiler 5 sur la pile vide donne 5 :: 2 :: nil.
*)

Definition stack := list nat.


Definition push n : stack -> stack := fun st => n :: st.


Definition exec_opbin (op : nat -> nat -> nat) : stack -> stack :=
  fun s => match s with
           | b :: a :: s => op a b :: s
           | _ => nil
           end.

(* Sémantique dénotationnelle d'une instruction *)
Definition exec_i (i : Instr) (s:Etat) : stack -> stack :=
  match i with
  | IpushC n => push n
  | Ifetch x => push (s x)
  | Iadd => exec_opbin Nat.add
  | Isub => exec_opbin Nat.sub
  | Imul => exec_opbin Nat.mul
  end.

(* Sémantique dénotationnelle d'un code (une suite d'instructions) *)

Fixpoint exec (c : Code) (s:Etat) (p:stack): stack :=
    match c with
      | nil => p
      | i :: c' =>  exec c' s (exec_i i s p)
    end.


(* On peut composer les executions de codes comme suit *)

Lemma comp_exec : forall c1 c2 s p1 p2 p3,
                  exec c1 s p1 = p2
               -> exec c2 s p2 = p3
               -> exec (c1++c2) s p1 = p3.
Proof.
              (* A COMPLETER - OPTIONNEL *)
Admitted.

(* L'exécution d'un code peut être peut être décomposée arbitrairement *)

Lemma dec_code :  forall c1 c2 s p, exec (c1++c2) s p = exec c2 s (exec c1 s p).
Proof.
  intros c1 c2 s.
  induction c1 as [| i c IH]. 
             (* A COMPLETER *)
Admitted.

(* La propriété suivante affirme qu'on peut ajouter des valeurs en fond
   de pile sans changer le résultat de l'exécution du code.

Lemma wrong_app_stack : forall c s p1 p1' p2, exec c s p1 = p1' -> exec c s (p1++p2) = p1'++p2.

Cette propriété est fausse! Trouvez un contre exemple.
 *)

(*
Compiler une expression arithmétique consiste à concaténer la compilation
des sous-expressions. Par exemple, compiler Apl a1 a2  consiste à concaténer
le code compilé de a1 au code de a2 et enfin à l'instruction Iadd.
*)

Fixpoint compileA (a : Aexp) : Code :=
  match a with
    | Anu (Nu n) => [IpushC n]
    | Ava x      => [Ifetch x]
    | Apl a1 a2  => compileA a1 ++ compileA a2 ++ [Iadd]
    | Amu a1 a2  => compileA a1 ++ compileA a2 ++ [Imul]
    | Amo a1 a2  => compileA a1 ++ compileA a2 ++ [Isub]
  end.

(* Exemples d'expressions arithmétiques *)

Definition ae2px := Apl (Anu (Nu 2)) (Ava x). (* 2 + x *)
Definition ae9m2px := Amo (Anu (Nu 9)) ae2px. (* 9 - (2 + x) *)

(* Exemples de code compilés *)

Compute (compileA ae2px).
Compute (compileA ae9m2px).

(* Exemples d'exécutions de code
   avec une pile initiale vide et un état ou x vaut 3
*)

Definition s3 := init_x 3.

Compute exec (compileA (Anu (Nu 2))) s3 nil.
Compute exec (compileA ae2px) s3 nil.
Compute exec (compileA ae9m2px) s3 nil.


(*
  Théorème de correction de la compilation.
  Version générale quelle que soit la pile de départ.
*)

Theorem correct_compileA_allp : forall (a : Aexp) (s : Etat) (p:stack),
                                exec (compileA a) s p = exec [IpushC (semA a s)] s p.
Proof.
  induction a as [[n] | x | a1 Ha1 a2 Ha2 | a1 Ha1 a2 Ha2 | a1 Ha1 a2 Ha2]; intros s p; cbn.
  - reflexivity.
  - reflexivity. 
             (* A COMPLETER *)
Admitted.

(* La sémantique dénotationnelle (avec une pile vide) d'un code obtenu
   par compilation de a est égale à la fonction qui empile la
   sémantique dénotationnelle de a.
*)

Corollary correct_compileA : forall (a : Aexp) (s:Etat),
                             exec (compileA a) s [] = push (semA a s) [].
Proof.   
             (* A COMPLETER *)
Admitted.

(* La propriété fausse wrong_app_stack est vraie pour le code compilé.
   On pourra utiliser la propriété de librairie
   Theorem app_comm_cons : forall (x y:list A) (a:A), a :: (x ++ y) = (a :: x) ++ y. *)

Lemma app_stack :  forall a s p1 p1' p2,
                  exec (compileA a) s p1 = p1' -> exec (compileA a) s (p1++p2) = p1'++p2.
Proof.             
           (* A COMPLETER - OPTIONNEL  *)
Admitted.
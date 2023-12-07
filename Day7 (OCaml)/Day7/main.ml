exception UnexpectedInput
exception EqualHands
exception DifferentSizeHands

let read_input filepath = 
  Core.In_channel.read_lines filepath

let sort_hands lines = 
  let get_card_val card =
    match int_of_string_opt card with
    | Some i -> i
    | None -> (match card with
      | "J" -> 11
      | "Q" -> 12
      | "K" -> 13
      | "A" -> 14
      | _ -> raise UnexpectedInput
    ) in
  let compare_hands hand_a hand_b =
    let rec compare_card_by_card remaining_a remaining_b =
      match remaining_a with
      | [] -> raise EqualHands
      | h :: t -> (match remaining_b with
        | [] -> raise DifferentSizeHands
        | o_h :: o_t -> 
          if h = o_h then compare_card_by_card t o_t
          else compare (get_card_val h) (get_card_val o_h)) in
    compare_card_by_card hand_a hand_b in
  List.sort (compare_hands) lines

let challenge_1 lines =

  List.length lines

let challenge_2 lines =
  List.length lines

let run_challenges input_lines = 
  Printf.printf "Challenge 1: %d\nChallenge 2: %d\n" 
    (challenge_1 input_lines) (challenge_2 input_lines)

let _ = run_challenges @@ read_input "input.txt"
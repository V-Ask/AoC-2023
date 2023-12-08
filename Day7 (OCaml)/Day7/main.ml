exception UnexpectedInput of char
exception EqualHands
exception DifferentSizeHands

module CharMap = Map.Make(Char)

let read_input filepath = 
  Core.In_channel.read_lines filepath

let explode_str str =
  List.init (String.length str) (String.get str)

let rec get_chr_freq str = 
  match str with
  | [] -> CharMap.empty
  | ' ' :: _ -> CharMap.empty
  | c :: rest -> (
    let prev = get_chr_freq rest in
    match CharMap.find_opt c prev with
    | None -> 
      CharMap.add c 1 prev
    | Some i -> 
      CharMap.add c (i + 1) prev
  )

let compare_hands hand_a hand_b winrank_val card_val =
  let hand_a_chars = explode_str hand_a in 
  let hand_b_chars = explode_str hand_b in 
  let compare_wins = compare (winrank_val hand_a_chars) (winrank_val
  hand_b_chars) in
  (match compare_wins with
  | 0 -> 
    (let rec compare_card_by_card remaining_a remaining_b =
    match remaining_a with
    | [] -> raise EqualHands
    | h :: t -> (match remaining_b with
      | [] -> raise DifferentSizeHands
      | o_h :: o_t -> 
        if h = o_h then compare_card_by_card t o_t
        else compare (card_val h) (card_val o_h)) in
  compare_card_by_card hand_a_chars hand_b_chars)
  | i -> i)

let sort_hands lines = 

  let get_card_winrank str =
    let map = get_chr_freq str in
    let values = CharMap.fold (fun _ value acc -> (
      value :: acc
    )) map [] |> List.sort compare |> List.rev in
    match List.nth values 0 with
    | 3 -> 
      (match List.nth values 1 with
      | 2 -> 3.5
      | _ -> 3.)
    | 2 -> (match List.nth values 1 with
      | 2 -> 2.5
      | _ -> 2.)
    | i -> float_of_int i in

  let get_card_val card =
    let card_s = String.make 1 card in
    match int_of_string_opt card_s with
    | Some i -> i
    | None -> (match card with
      | 'T' -> 10
      | 'J' -> 11
      | 'Q' -> 12
      | 'K' -> 13
      | 'A' -> 14
      | other -> raise (UnexpectedInput other)
    ) in
  let compare_hands_no_j hand_a hand_b = compare_hands hand_a hand_b
  get_card_winrank get_card_val in
  List.sort (compare_hands_no_j) lines

let calc_bid_winnings sorted_card_list = 
  let sum, _ = List.fold_left (fun (acc, index) card ->
    let bid = List.nth (String.split_on_char ' ' card) 1 |> int_of_string in
    acc + (index * bid), index + 1
) (0, 1) sorted_card_list in
sum

let challenge_1 lines =
  let sorted = sort_hands lines in
  calc_bid_winnings sorted

let sort_hands_j lines = 
  let get_card_val c =
    match int_of_string_opt (String.make 1 c) with
    | Some i -> i
    | None -> (match c with
      | 'T' -> 10
      | 'J' -> 0
      | 'Q' -> 12
      | 'K' -> 13
      | 'A' -> 14
      | other -> raise (UnexpectedInput other)
    ) in
  let get_card_winrank_j hand =
    let map = get_chr_freq hand in
    let jokers = (match CharMap.find_opt 'J' map with
    | None -> 0.
    | Some i -> float_of_int i) in
    let values = CharMap.fold (fun c value acc -> (
      if (c = 'J') then acc else value :: acc
    )) map [] |> List.sort compare |> List.rev in
    match List.nth_opt values 0 with
    | None -> 5.
    | Some i ->
      let highest_amount = (float_of_int @@ i) +. jokers in
      if (highest_amount > 4.5) then highest_amount
      else (match highest_amount with
      | 3. -> 
        (match List.nth values 1 with
        | 2 -> 3.5
        | _ -> 3.)
      | 2. -> (match List.nth values 1 with
        | 2 -> 2.5
        | _ -> 2.)
      | i -> i) in
      

  let compare_hands_j hand_a hand_b = compare_hands hand_a hand_b
  get_card_winrank_j get_card_val in
  List.sort (compare_hands_j) lines

let challenge_2 lines =
  let sorted = sort_hands_j lines in
  calc_bid_winnings sorted

let run_challenges input_lines = 
  Printf.printf "Challenge 1: %d\nChallenge 2: %d\n" 
    (challenge_1 input_lines) (challenge_2 input_lines)

let _ = run_challenges @@ read_input "input.txt"
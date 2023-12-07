
DIGITS = ['ZERO', 'ONE', 'TWO', 'THREE', 'FOUR', 'FIVE', 'SIX', 'SEVEN', 'EIGHT', 'NINE']

def get_digit (line : str, digit_list) -> int:
    for i in range(0, len(line)):
        if line[i].isnumeric():
            return line[i]
        for j in range(len(digit_list)):
            digit = digit_list[j]
            dig_len = len(digit)
            sb_str = line[i:i+dig_len]
            if sb_str == digit.lower():
                return j
    return ""

def get_sum (file_lines : [str]) -> int:
    cur_sum = 0
    for line in file_lines:
        frst = get_digit(line, DIGITS)
        rev_dgts = [x[::-1] for x in DIGITS]
        last = get_digit(line[::-1], rev_dgts)
        digit_str = f'{frst}{last}'
        if digit_str == "": 
            continue
        print(digit_str)
        digit = int(digit_str)
        cur_sum = cur_sum + digit
    return cur_sum


file = open('input.txt', 'r')
print(get_sum(file.readlines()))
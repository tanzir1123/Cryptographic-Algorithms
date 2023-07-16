import binascii
from math import floor
import random
s_box = [
    0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
    0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
    0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
    0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
    0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
    0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
    0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
    0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
    0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
    0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
    0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
    0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
    0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
    0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
    0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
    0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
]

matrix = [2, 3, 1, 1,
          1, 2, 3, 1,
          1, 1, 2, 3,
          3, 1, 1, 2]

inv_matrix = [14, 11, 13, 9,
              9, 14, 11, 13,
              13, 9, 14, 11,
              11, 13, 9, 14]

rcon = [
    "01", "00", "00", "00", "02", "00", "00", "00",
    "04", "00", "00", "00", "08", "00", "00", "00",
    "10", "00", "00", "00", "20", "00", "00", "00",
    "40", "00", "00", "00", "80", "00", "00", "00",
    "1b", "00", "00", "00", "36", "00", "00", "00"
]


def hexify(text):
    hex_list = []
    text_bytes = text.encode('utf-8')  # Convert the text to bytes using UTF-8 encoding
    hex_text = binascii.hexlify(text_bytes)
    for num in range(0, len(hex_text), 2):
        hex_list.append(hex_text[num:num + 2].decode('utf-8'))  # Convert each pair of bytes back to a string
    return hex_list


def zero(byte):
    if len(byte) < 2:
        return "0" + byte
    return byte


def xor(message, key):
    xored = []
    for i in range(len(key)):
        xored.append(zero(hex(int(message[i], 16) ^ int(key[i], 16))[2:]))
    return xored


def padder(message):
    global reps
    if len(message) > 16:
        if len(message) % 16 != 0:
            message += "0" * (16 - (len(message) % 16))
        reps = len(message) // 16
    elif len(message) < 16:
        message += "0" * (16 - len(message))
        reps += 1
    else:
        reps = 1
    return message


def bin_padding(binary):
    binary = bin(binary)[2:]
    return "0" * (8 - len(str(binary))) + binary


def key_check(key):
    if len(key) < 16:
        key += "0" * (16 - len(key))
        print(key)
    elif len(key) > 16:
        print("Error: Key bigger than 128 bits.")
        sys.exit(1)


def multiply(number, original, triply=0):
    old_number = bin_padding(number)
    number = bin(number << 1)[2:]
    if len(number) > 8:
        number = number[len(number) - 8:]
    if old_number[0] == "1":
        number = int(number, 2) ^ int(bin(int("1B", 16)), 2)
    else:
        number = int(number, 2)
    if triply == 1:
        number ^= int(original, 2)
    return number


def hex2ascii(hex):
    ascii = []
    for num in range(0, len(hex), 2):
        ascii.append(hex[num:num + 2])
    return ascii


def SubBytes(message):
    subbed = []
    list(map(lambda char: subbed.append(hex(s_box[int(zero(char)[0], 16) * 16 + int(char[-1], 16)])[2:]), message))
    return subbed


def ShiftRows(message):
    shifted = []
    list(map(lambda char: shifted.append(zero(char)), message))

    for i in range(1, 4):
        for k in range(i):
            for j in range(0, 3):
                shifted[i], shifted[i + 12 - j * 4] = shifted[i + 12 - j * 4], shifted[i]
    return shifted


def MixColumns(message):
    mixed = []
    for k in range(4):
        for i in range(4):
            cell = 0
            for j in range(4):
                current = int(bin(int(message[j + 4 * k], 16)), 2)
                old = bin_padding(current)

                if matrix[j + i * 4] == 2:
                    current = multiply(current, old)

                elif matrix[j + i * 4] == 3:
                    current = multiply(current, old, 1)

                cell = int(bin(cell), 2) ^ current
            mixed.append(zero(hex(cell)[2:]))
    return mixed


def KeySchedule(key, t):
    segment, newkey = [], []
    for i in range(12, 16):
        segment.append(key[i])
    for i in range(3, 0, -1):
        segment[0], segment[i] = segment[i], segment[0]
    segment = SubBytes(segment)
    i = 0
    while len(newkey) < 4:
        newkey.append(hex(int(key[i], 16) ^ int(segment[i], 16) ^ int(rcon[4 * t + i], 16))[2:])
        i += 1
    for i in range(12):
        newkey.append(hex(int(key[i + 4], 16) ^ int(newkey[i], 16))[2:])

    return newkey


def invSubBytes(ciphertext):
    unsubbed = []
    for char in ciphertext:
        char = "0x" + char
        unsubbed.append(
            "%X" % (floor(s_box.index(int(char, 16)) / 16)) + "%X" % (s_box.index(int(char, 16)) % 16))

    return unsubbed


def invShiftRows(ciphertext):
    unshifted = []
    list(map(lambda char: unshifted.append(zero(char)), ciphertext))

    for i in range(1, 4):
        for k in range(i):
            for j in range(3, 0, -1):
                unshifted[i + 12], unshifted[i + 12 - j * 4] = unshifted[i + 12 - j * 4], unshifted[i + 12]
    return unshifted


def invMixColumns(ciphertext):
    unmixed = []
    for k in range(4):
        for l in range(4):
            cell = 0
            for j in range(0, 4):
                current = int(bin(int(ciphertext[j + 4 * k], 16)), 2)
                old = bin_padding(current)

                if inv_matrix[j + l * 4] == 9:
                    for i in range(2):
                        current = multiply(current, old)
                    current = multiply(current, old, 1)

                elif inv_matrix[j + l * 4] == 11:
                    current = multiply(current, old)
                    for i in range(2):
                        current = multiply(current, old, 1)

                elif inv_matrix[j + l * 4] == 13:
                    current = multiply(current, old, 1)
                    current = multiply(current, old)
                    current = multiply(current, old, 1)

                elif inv_matrix[j + l * 4] == 14:
                    for i in range(2):
                        current = multiply(current, old, 1)
                    current = multiply(current, old)

                cell = int(bin(cell), 2) ^ current

            unmixed.append(zero(hex(cell)[2:]))
    return unmixed


def ECBencrypt(message, key):
    original = key
    final = ""

    for rep in range(1, int(reps) + 1):
        xored = xor(message[((rep - 1) * 16):(16 * rep)], original)

        for j in range(1, 10):
            keyx = original
            for i in range(j):
                keyx = KeySchedule(keyx, i)
            subbed = SubBytes(xored)
            shifted = ShiftRows(subbed)
            mixed = MixColumns(shifted)
            xored = xor(mixed, keyx)

        subbed = SubBytes(xored)
        shifted = ShiftRows(subbed)
        key = KeySchedule(keyx, 9)

        final += "".join(xor(shifted, key))
    return final


def ECBdecrypt(ciphertext, originalkey):
    message = ""
    lastkey = originalkey

    for i in range(10):
        lastkey = KeySchedule(lastkey, i)

    for rep in range(1, int(reps) + 1):
        xored = xor(ciphertext[((rep - 1) * 16):(16 * rep)], lastkey)

        unshifted = invShiftRows(xored)
        unsubbed = invSubBytes(unshifted)

        for round in range(9, 0, -1):
            key = originalkey
            for kround in range(round):
                key = KeySchedule(key, kround)
            xored = xor(unsubbed, key)
            unmixed = invMixColumns(xored)
            unshifted = invShiftRows(unmixed)
            unsubbed = invSubBytes(unshifted)

        message += "".join(xor(unsubbed, originalkey))
    return binascii.unhexlify(message)




print("-----------------AES-----------------\n\n")
reps = 0

key = hexify("keyis:1201303219")
choice = input("1. Encryption, 2. Decryption, 3. Error Impact\n")
if choice == "1":
    # ask user for the message to be encrypted
    msg = input("Enter the message to be encrypted: ")
    message = padder(hexify(msg))
    result = ECBencrypt(message, key).upper()
    print("The pre-selected 128-bit key: ", key)
    print("Cipher text: ", result)
    

elif choice == "2":
    cipher = input("Enter the Cipher text: ")
    result = ECBdecrypt(padder(hex2ascii(cipher.upper())), key)
    print("The pre-selected 128-bit key: ", key)
    print("Plain text: ", result.decode('utf-8'))

elif choice == "3":
    
    msg = input("Enter the Original Message: ")
    message = padder(hexify(msg))
    cipher = ECBencrypt(message, key).upper()
    print("Original Message: ", msg)
    print("Cipher: ", cipher)

    print("Changing bit in the Cipher Text Randomly")

    position = random.randint(0, len(cipher) - 1)
    bit_to_flip = random.randint(0, 7)

    byte = int(cipher[position], 16)  # Convert the character to integer
    modified_byte = byte ^ (1 << bit_to_flip)  # Flipping the selected bit
    modified_ciphertext = cipher[:position] + hex(modified_byte)[2:] + cipher[position + 1:]

    print("modified ciphertext: ", modified_ciphertext)
    decrypted = ECBdecrypt(padder(hex2ascii(modified_ciphertext.upper())), key)
    if decrypted == msg:
        print("Decryption successful")
    else:
        print("Decryption failed. Bit error detected.")




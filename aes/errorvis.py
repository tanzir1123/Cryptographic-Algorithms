import random
from AESmain import ECBencrypt, ECBdecrypt

# Function to introduce bit errors in the ciphertext
def introduce_bit_error(ciphertext):
    position = random.randint(0, len(ciphertext) - 1)
    bit_to_flip = random.randint(0, 7)
    byte = ciphertext[position]
    modified_byte = byte ^ (1 << bit_to_flip)  # Flipping the selected bit
    modified_ciphertext = ciphertext[:position] + bytes([modified_byte]) + ciphertext[position + 1:]
    return modified_ciphertext

# Function to decrypt ciphertext and check for bit errors
def decrypt_with_bit_errors(ciphertext, key, original_message):
    decrypted = ECBdecrypt(ciphertext, key)
    if decrypted == original_message:
        print("Decryption successful")
    else:
        print("Decryption failed. Bit error detected.")

# Main function
def main():
    # Provide the original message and encryption key
    original_message = "This is a secret message"
    key = "EncryptionKey123"

    # Encrypt the original message
    ciphertext = ECBencrypt(original_message, key)
    print("Original Ciphertext:", ciphertext.hex())

    # Introduce a bit error in the ciphertext
    modified_ciphertext = introduce_bit_error(ciphertext)
    print("Modified Ciphertext:", modified_ciphertext.hex())

    # Decrypt the modified ciphertext with bit errors
    decrypt_with_bit_errors(modified_ciphertext, key, original_message)

if __name__ == '__main__':
    main()

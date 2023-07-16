import random
import math

def is_prime(number):
    if number < 2:
        return False
    
    for i in range(2, number // 2 + 1):
        if number % i == 0:
            return False
    
    return True

def generate_prime(min_value, max_value): 
    prime = random.randint(min_value, max_value)

    while not is_prime(prime):
        prime = random.randint(min_value, max_value)
    return prime

def mod_inverse(e, phi):
    for d in range(3, phi):
        if (e * d) % phi == 1:
            return d
    raise ValueError("modular inverse does not exist")

p, q = generate_prime(1000, 5000), generate_prime(1000, 5000)

while p == q:
    q = generate_prime(1000, 5000)

n = p * q
phi_n = (p - 1) * (q - 1)

e = random.randint(3, phi_n-1)

while math.gcd(e, phi_n) != 1:
    e = random.randint(3, phi_n-1)

d = mod_inverse(e, phi_n)

print("Public key: ", e)
print("Private key: ", d)
print("n: ", n)
print("Phi of n: ", phi_n)
print("p: ", p)
print("q: ", q)



print("------- RSA -------\n\n")
message = "Do not attack now."

# ENCRYPTION:

# encoding as ASCII characters
message_encoded = [ord(ch) for ch in message]

# c = (m ^ e) mod n
ciphertext = [pow(ch, e, n) for ch in message_encoded] # pow(ch, e, n) = (ch ^ e) mod n

print("Cipher text: ")
print(ciphertext, "\n")


# DECRYPTION:

message_encoded = [pow(ch, d, n) for ch in ciphertext] # pow(ch, d, n) = (ch ^ d) mod n)]

# decoding as ASCII characters
message = "".join(chr(ch) for ch in message_encoded)
print("Plain Text: ", message)

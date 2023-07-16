import numpy as np
import matplotlib.pyplot as plt

# Data for Affine Cipher
affine_input_size = [5, 100, 1000, 5000]
affine_encryption_time = [643, 1124, 7458, 65213]
affine_decryption_time = [1246, 862, 4464, 29581]

# Data for Columnar Transposition (Double) Cipher
columnar_input_size = [5, 100, 1000, 5000]
columnar_encryption_time = [1921, 18460, 119112, 468342]
columnar_decryption_time = [2207, 8483, 126810, 468366]

# Set the bar width
bar_width = 0.35

# Set the positions of the bars on the x-axis
index = np.arange(len(affine_input_size))

# Plotting the graph
fig, ax = plt.subplots()
bar1 = ax.bar(index, affine_encryption_time, bar_width, label='Affine Encryption', color='yellow')
bar2 = ax.bar(index + bar_width, affine_decryption_time, bar_width, label='Affine Decryption', color='green')

# Adding labels and titles
ax.set_xlabel('Input Size (N)')
ax.set_ylabel('Time Taken (us)')
ax.set_title('Classical Encryption Algorithms: Time Taken for Different Input Sizes')
ax.set_xticks(index + bar_width / 2)
ax.set_xticklabels(affine_input_size)
ax.legend()

# Displaying the graph
plt.tight_layout()
plt.show()

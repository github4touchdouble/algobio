import matplotlib.pyplot as plt
import seaborn as sns

if __name__ == "__main__":

    # Data
    x_labels = ["Default Dynamic Programming", "Optimized Dynamic Programming"]
    y_values = [30808, 44069]

    # Create the bar plot
    plt.figure(figsize=(10, 6))
    plt.bar(x_labels, y_values)

    # Add labels and title
    plt.xlabel("Algorithm", fontsize=17)
    plt.ylabel("n", fontsize=17)
    plt.title("Comparison of Input Size", fontsize=17)
    plt.xticks(fontsize=15)
    plt.yticks(fontsize=15)

    # Show the plot
    plt.show()


import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import sys


if __name__ == "__main__":

    path = sys.argv[1]
    df = pd.read_csv(path, sep=";")

    print(df.head())


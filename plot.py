import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import sys
import argparse

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="description")
    parser.add_argument('-p', type=str, required=True)
    parser.add_argument('-m', type=str, required=True)
    args = parser.parse_args()

    path = args.p
    metric = args.m

    df = pd.read_csv(path, sep=";")

    sns.set_theme("paper")
    sns.set_palette("colorblind")
    sns.set_style("ticks")
    plt.figure(figsize=(10, 6))

    plt.plot(df["n"], df["smss"], label="smss")
    plt.plot(df["n"], df["optimal"], label="optimal")
    plt.plot(df["n"], df["divide"], label="divide")
    plt.plot(df["n"], df["dynamic"], label="dynamic")
    plt.plot(df["n"], df["naive"], label="naive")

    plt.xlabel("n", fontsize=15)
    plt.ylabel(f"time in {metric}", fontsize=15)
    plt.xticks(fontsize=15)
    plt.yticks(fontsize=15)
    plt.legend(fontsize=15)
    sns.despine()

    plt.savefig(bbox_inches="tight", fname=sys.argv[1].split(".")[0])


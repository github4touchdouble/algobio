import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import argparse
import matplotlib.ticker as ticker


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="description")
    parser.add_argument('-p', type=str, required=True)
    args = parser.parse_args()

    path = args.p

    df = pd.read_csv(path, sep=";")

    sns.set_theme("paper")
    sns.set_palette("colorblind")
    sns.set_style("ticks")
    plt.figure(figsize=(10, 6))

    for col_name in df.columns:
        if col_name != "n":
            plt.plot(df["n"], df[col_name] / 1e6 , label=col_name)

    plt.xlabel("n", fontsize=20)
    plt.ylabel(f"time in seconds", fontsize=20)
    plt.xticks(fontsize=20)
    plt.yticks(fontsize=20)
    plt.legend(fontsize=20)
    sns.despine()
    plt.gca().yaxis.set_major_formatter(ticker.FuncFormatter(lambda x, _: f'{x:.0f}'))

    plt.savefig(bbox_inches="tight", fname=path.split(".")[0])


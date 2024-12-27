import pandas as pd
import matplotlib.pyplot as plt
from numerize import numerize

# Load the data from CSV file
df = pd.read_csv("results.csv")

def plotPlan(df):
    state1_data = df[df["InitialState"] == "State 1"]
    state2_data = df[df["InitialState"] == "State 2"]

    strategies = state1_data["Strategy"]
    x = range(len(strategies))

    plt.figure(figsize=(4, 4))

    plt.bar(
        [i - 0.2 for i in x],
        state1_data["PlanLength"],
        width=0.4,
        label="State 1",
        color="r",
    )
    plt.bar(
        [i + 0.2 for i in x],
        state2_data["PlanLength"],
        width=0.4,
        label="State 2",
        color="#722F37",
    )

    # Add a black dotted line at y = 31
    plt.axhline(y=31, color="black", linestyle="--", linewidth=1, label="Min. actions: 31")
    
    plt.xticks(x, strategies, rotation=45)
    plt.title("Plan Length vs Search Algorithms")
    plt.ylabel("Plan Length")
    plt.legend()
    plt.tight_layout()

def plotUnique(df):
    state1_data = df[df["InitialState"] == "State 1"]
    state2_data = df[df["InitialState"] == "State 2"]

    strategies = state1_data["Strategy"]
    x = range(len(strategies))

    plt.figure(figsize=(4, 4))

    plt.bar(
        [i - 0.2 for i in x],
        state1_data["StatesDiscovered"],
        width=0.4,
        label="State 1",
        color="g",
    )
    plt.bar(
        [i + 0.2 for i in x],
        state2_data["StatesDiscovered"],
        width=0.4,
        label="State 2",
        color="#4b5c09",
    )

    # Customize y-axis ticks using numerize
    def format_y_ticks(value, _):
        return numerize.numerize(value)

    plt.gca().yaxis.set_major_formatter(plt.FuncFormatter(format_y_ticks))
    
    # Add a black dotted line at y = 31
    plt.axhline(y=181440, color="black", linestyle="--", linewidth=1, label="Search Space: 181.44k")
    
    plt.xticks(x, strategies, rotation=45)
    plt.title("Unique States vs Search Algorithms")
    plt.ylabel("States Discovered")
    plt.ylim(0, 210000)  
    plt.legend()
    plt.tight_layout()

def plotDuration(df):
    state1_data = df[df["InitialState"] == "State 1"]
    state2_data = df[df["InitialState"] == "State 2"]

    strategies = state1_data["Strategy"]
    x = range(len(strategies))

    plt.figure(figsize=(4, 4))

    plt.bar(
        [i - 0.2 for i in x],
        state1_data["TimeTaken"],
        width=0.4,
        label="State 1",
        color="b",
    )
    plt.bar(
        [i + 0.2 for i in x],
        state2_data["TimeTaken"],
        width=0.4,
        label="State 2",
        color="#000080",
    )

    plt.xticks(x, strategies, rotation=45)
    plt.title("Duration vs Search Algorithms")
    plt.ylabel("Time Taken (ms)")
    plt.ylim(0, 1000)  
    plt.legend()
    plt.tight_layout()

plotPlan(df)
plotUnique(df)
plotDuration(df)

# Show the plots
plt.show()

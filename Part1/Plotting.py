import pandas as pd
import matplotlib.pyplot as plt
from numerize import numerize

# Load the data from CSV file
df = pd.read_csv("results.csv")


def plotPlan(df):
    # Filter data for the given state
    state1_data = df[df["InitialState"] == "State 1"]
    state2_data = df[df["InitialState"] == "State 2"]

    # Create a figure and axis
    plt.figure(figsize=(4, 4))

    # Plot Plan Length vs Search Algorithms
    plt.plot(
        state1_data["Strategy"],
        state1_data["PlanLength"],
        marker="o",
        label="State 1",
        color="r",
        linestyle="-",
    )
    for i, value in enumerate(state1_data["PlanLength"]):
        plt.annotate(
            f"{value}",
            (state1_data["Strategy"].iloc[i], state1_data["PlanLength"].iloc[i]),
            textcoords="offset points",
            xytext=(0, 5),
            ha="center",
        )

    # Plot Plan Length vs Search Algorithms
    plt.plot(
        state2_data["Strategy"],
        state2_data["PlanLength"],
        marker="o",
        label="State 2",
        color="#722F37",
        linestyle="-",
    )
    for i, value in enumerate(state2_data["PlanLength"]):
        plt.annotate(
            f"{value}",
            (state2_data["Strategy"].iloc[i], state2_data["PlanLength"].iloc[i]),
            textcoords="offset points",
            xytext=(0, -14),
            ha="center",
        )

    # Adding title and labels
    plt.title(f"Plan Length vs Search Algorithms")
    plt.ylabel("States")

    # Adding legend
    plt.legend()

    # Rotate x-axis labels for better readability
    plt.xticks(rotation=80)

    # Adjust layout for better spacing
    plt.tight_layout()


def plotUnique(df):
    # Filter data for the given state
    state1_data = df[df["InitialState"] == "State 1"]
    state2_data = df[df["InitialState"] == "State 2"]

    # Create a figure and axis
    plt.figure(figsize=(4, 4))

    # Plot StatesDiscovered vs Search Algorithms
    plt.plot(
        state1_data["Strategy"],
        state1_data["StatesDiscovered"],
        marker="o",
        label="State 1",
        color="#4b5c09",
        linestyle="-",
    )

    plt.plot(
        state2_data["Strategy"],
        state2_data["StatesDiscovered"],
        marker="o",
        label="State 2",
        color="g",
        linestyle="-",
    )  # Annotate with formatted values only when values differ

    for i, (value1, value2) in enumerate(
        zip(state1_data["StatesDiscovered"], state2_data["StatesDiscovered"])
    ):
        formatted_value1 = numerize.numerize(value1, 1)  # Format the first value
        formatted_value2 = numerize.numerize(value2, 1)  # Format the second value

        if numerize.numerize(value1,1) != numerize.numerize(value2,1):  # Check if the values are different
            # Annotate for State 1
            plt.annotate(
                f"{formatted_value1}",
                (
                    state1_data["Strategy"].iloc[i],
                    state1_data["StatesDiscovered"].iloc[i],
                ),
                textcoords="offset points",
                xytext=(0, 5),
                ha="center",
            )

            # Annotate for State 2
            plt.annotate(
                f"{formatted_value2}",
                (
                    state2_data["Strategy"].iloc[i],
                    state2_data["StatesDiscovered"].iloc[i],
                ),
                textcoords="offset points",
                xytext=(0, -14),
                ha="center",
            )
        else:
            # Annotate for State 1
            if i%2==0:
                plt.annotate(
                    f"{formatted_value1}",
                    (
                        state1_data["Strategy"].iloc[i],
                        state1_data["StatesDiscovered"].iloc[i],
                    ),
                    textcoords="offset points",
                    xytext=(0, 5),
                    ha="center",
                )
            else:
                plt.annotate(
                    f"{formatted_value1}",
                    (
                        state1_data["Strategy"].iloc[i],
                        state1_data["StatesDiscovered"].iloc[i],
                    ),
                    textcoords="offset points",
                    xytext=(0, -14),
                    ha="center",
                )

    # Customize y-axis ticks using numerize
    def format_y_ticks(value, _):
        return numerize.numerize(value)

    plt.gca().yaxis.set_major_formatter(plt.FuncFormatter(format_y_ticks))

    # Adding title and labels
    plt.title(f"Unique States vs Search Algorithms")
    plt.ylabel("States")

    # Adding legend
    plt.legend()

    # Rotate x-axis labels for better readability
    plt.xticks(rotation=80)

    # Adjust layout for better spacing
    plt.tight_layout()


def plotDuration(df):
    # Filter data for the given state
    state1_data = df[df["InitialState"] == "State 1"]
    state2_data = df[df["InitialState"] == "State 2"]

    # Create a figure and axis
    plt.figure(figsize=(4, 4))

    # Plot TimeTaken vs Search Algorithms
    plt.plot(
        state1_data["Strategy"],
        state1_data["TimeTaken"],
        marker="o",
        label="State 1",
        color="b",
        linestyle="-",
    )
    for i, value in enumerate(state1_data["TimeTaken"]):
        plt.annotate(
            f"{value}",
            (state1_data["Strategy"].iloc[i], state1_data["TimeTaken"].iloc[i]),
            textcoords="offset points",
            xytext=(0, 5),
            ha="center",
        )

    plt.plot(
        state2_data["Strategy"],
        state2_data["TimeTaken"],
        marker="o",
        label="State 2",
        color="#000080",
        linestyle="-",
    )
    for i, value in enumerate(state2_data["TimeTaken"]):
        plt.annotate(
            f"{value}",
            (state2_data["Strategy"].iloc[i], state2_data["TimeTaken"].iloc[i]),
            textcoords="offset points",
            xytext=(0, -14),
            ha="center",
        )

    # Adding title and labels
    plt.title(f"Duration vs Search Algorithms")
    plt.ylabel("States")

    # Adding legend
    plt.legend()

    # Rotate x-axis labels for better readability
    plt.xticks(rotation=80)

    # Adjust layout for better spacing
    plt.tight_layout()


plotDuration(df)

plotUnique(df)

plotPlan(df)

# Show the plot
plt.show()

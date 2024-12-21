import pandas as pd
import matplotlib.pyplot as plt

# Load the CSV file into a DataFrame
data = pd.read_csv("results.csv")

# Separate data by initial states
state_1_data = data[data["InitialState"] == "State 1"]
state_2_data = data[data["InitialState"] == "State 2"]

# Plot configurations
metrics = ["PlanLength", "StatesDiscovered", "TimeTaken"]
titles = ["Plan Length", "States Discovered", "Time Taken (ms)"]
colors = ["blue", "green", "red"]

# Create subplots for each metric and state
fig, axes = plt.subplots(2, 3, figsize=(18, 10), sharey="col")
states = [state_1_data, state_2_data]
state_titles = ["State 1", "State 2"]

for state_idx, state_data in enumerate(states):
    for metric_idx, metric in enumerate(metrics):
        axes[state_idx, metric_idx].bar(
            state_data["Strategy"], state_data[metric], color=colors[metric_idx]
        )
        axes[state_idx, metric_idx].set_title(f"{titles[metric_idx]} ({state_titles[state_idx]})")
        axes[state_idx, metric_idx].set_ylabel(titles[metric_idx])
        axes[state_idx, metric_idx].set_xlabel("Strategy")
        axes[state_idx, metric_idx].tick_params(axis="x", rotation=45)

# Adjust layout for better fit
plt.tight_layout()
plt.show()
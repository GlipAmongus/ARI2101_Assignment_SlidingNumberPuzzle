import pandas as pd
import matplotlib.pyplot as plt

# Load the data from CSV file
df = pd.read_csv('results.csv')

# Function to plot the 3 metrics (Duration, Unique States, Plan Length) on a single graph for a given initial state
def plotStatesVsSearch(df, state):
    # Filter data for the given state
    state_data = df[df['InitialState'] == state]
    
    # Create a figure and axis
    plt.figure(figsize=(10, 6))
    
    # Plot Unique States vs Search Algorithms
    plt.plot(state_data['Strategy'], state_data['StatesDiscovered'], marker='o', label='Unique States', color='g', linestyle='-')
    
    # Plot Plan Length vs Search Algorithms
    plt.plot(state_data['Strategy'], state_data['PlanLength'], marker='o', label='Plan Length', color='r', linestyle='-')

    # Adding title and labels
    plt.title(f'States vs Search Algorithms ({state})')
    plt.xlabel('Search Algorithms')
    plt.ylabel('States')
    
    # Adding legend
    plt.legend()

    # Rotate x-axis labels for better readability
    plt.xticks(rotation=45)

    # Adjust layout for better spacing
    plt.tight_layout()


def plotDurationVsSearch(df, state):
    # Filter data for the given state
    state_data = df[df['InitialState'] == state]
    
    # Create a figure and axis
    plt.figure(figsize=(10, 6))

    # Plot Duration vs Search Algorithms
    plt.plot(state_data['Strategy'], state_data['TimeTaken'], marker='o', label='Duration (ms)', color='b', linestyle='-')

    # Adding title and labels
    plt.title(f'Duration vs Search Algorithms ({state})')
    plt.xlabel('Search Algorithms')
    plt.ylabel('MilliSeconds')
    
    # Adding legend
    plt.legend()

    # Rotate x-axis labels for better readability
    plt.xticks(rotation=45)

    # Adjust layout for better spacing
    plt.tight_layout()
    
# Plot for State 1
plotDurationVsSearch(df, 'State 1')

# Plot for State 2
plotDurationVsSearch(df, 'State 2')

# Plot for State 1
plotStatesVsSearch(df, 'State 1')

# Plot for State 2
plotStatesVsSearch(df, 'State 2')

# Show the plot
plt.show()
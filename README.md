# Data61Visualization
Network data visualization for CORA co-author publication network.

It draws a graph in which nodes represent authors and edges show co-author relationship. 

# Features
  
  - Drawing a graph using OpenGL.

  - Drawing tooltip to show properties of a node.

  - Centring a graph around an individual node.

  - Rotating graph about a point.   

# Instruction

  - Copy data files in root folder(beside pom.xml file)
  
  - Run GraphVisualisationApp.java 

  - To see tooltip, move mouse over a node. It shows author name, the number of papers, and the number of co-authors

  - To center a graph around a node, click on that node. (A node is selected when tooltip is present)

  - To rotate graph, press Alt-R

  - To stop rotation, press Alt-Shift-R 

# Future work

  - Currently the position of a node is determined randomly. To improve it, force-directed graph drawing algorithms is being implemented to show graphs in an aesthetically pleasing way.(An initial implementation is available in branch 'develop')  
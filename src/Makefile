#
# A simple makefile for compiling three java classes
#

# define a makefile variable for the java compiler
#
JCC = javac

# define a makefile variable for compilation flags
# the -g flag compiles with debugging information
#
JFLAGS = -g

# typing 'make' will invoke the first target entry in the makefile
# (the default one in this case)
#
default: GreedyHeuristic.class myGeneticAlgo.class Timer.class TSP.class TSP_Optimizer.class

# this target entry builds the Average class
# the Average.class file is dependent on the Average.java file
# and the rule associated with this entry gives the command to create it
#
GreedyHeuristic.class: GreedyHeuristic.java
	$(JCC) $(JFLAGS) GreedyHeuristic.java

myGeneticAlgo.class: myGeneticAlgo.java
	$(JCC) $(JFLAGS) myGeneticAlgo.java

Timer.class: Timer.java
	$(JCC) $(JFLAGS) Time.java

TSP.class: TSP.java
	$(JCC) $(JFLAGS) TSP.java

TSP_Optimizer.class: TSP_Optimizer.java
	$(JCC) $(JFLAGS) TSP_Optimizer.java

# To start over from scratch, type 'make clean'.
# Removes all .class files, so that the next make rebuilds them
#
clean:
	$(RM) *.class

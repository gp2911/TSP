import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: GP
 * Date: 08/10/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;

public class myGeneticAlgo extends Thread{
    float mutateRate;
    TSP_Optimizer optimizer;
    int worldSize ;
    int interval;
    int cchoice;
    int fchoice;
    LinkedList<Integer>[] currWorld;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public myGeneticAlgo(TSP_Optimizer t, boolean initialise){
        optimizer = t;
        if(initialise){
            //if initialise is true, initialise the GA engine...else pack.
            try{
                System.out.println("Starting the GA engine....");
                System.out.println("Enter the population size : ");
                int worldSize = Integer.parseInt(br.readLine());
                this.worldSize = worldSize;
                System.out.println("Enter the mutation rate : ");
                float mutationRate = Float.parseFloat(br.readLine());
                this.mutateRate = mutationRate;
                System.out.println("Enter the number of iterations after which you want feedback : ");
                int intervalSize = Integer.parseInt(br.readLine());
                interval = intervalSize;
                System.out.println("Your choice of crossover?");
                System.out.println("1. Single point crossover");
                System.out.println("2. Cyclic crossover");
                int crossoverChoice = Integer.parseInt(br.readLine());
                cchoice = crossoverChoice;
                System.out.println("Your choice of fitness?");
                System.out.println("1. Total Length of path");
                int fitnessChoice = Integer.parseInt(br.readLine());
                fchoice = fitnessChoice;
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        else
            worldSize=5;


        currWorld = new LinkedList[worldSize];
        synchronized (optimizer){
            currWorld[0] = optimizer.bestPath;
        }

        for(int i = 1; i < worldSize; i++){
            LinkedList newList = (LinkedList)optimizer.bestPath.clone();
            Collections.shuffle(newList);
            currWorld[i] = newList;
        }

    }

    // get the fittest member of the population depending on the fitness function
    public LinkedList<Integer> getFittest(LinkedList<Integer>[] pop){
        LinkedList<Integer> currPath = pop[0];
        double bestLen = 0;
        if(fchoice == 1)
            bestLen = calcPath(currPath);
        for(int i = 0; i < worldSize; i++){
            double len = calcPath(pop[i]);
            if(len < bestLen){
                bestLen = len;
                currPath = pop[i];
            }
        }
        synchronized (optimizer){
            if(optimizer.currentBest > bestLen){
                optimizer.currentBest = bestLen;
                optimizer.bestPath = currPath;
            }
        }

        return currPath;

    }
    // Fitness function 1 : Length of path
    public double calcPath(LinkedList<Integer> path){
        int prev = 0;
        int now = 1;
        int currCity = 0;
        int prevCity = 0;
        double len = 0;
        try{
            while(now < optimizer.num_of_cities - 1){
                currCity = path.get(now);
                prevCity = path.get(prev);
                len = len + optimizer.edgeLengths[prevCity-1][currCity-1];
                prev = now;
                now++;
            }
        }

        catch (Exception e){
            synchronized (System.out){
                e.printStackTrace();
                System.out.println(path);
                System.out.println(now + " : "+ prev);
                System.out.println(path.size());
                System.exit(1);
            }
        }

        len = len + optimizer.edgeLengths[path.get(now)-1][path.get(0)-1];
        return len;
    }

    //crossover function 2 : cyclic crossover
    public LinkedList<Integer> cyclicCrossover(LinkedList<Integer> P1, LinkedList<Integer> P2) {

//        System.out.println("Cyclic");
        LinkedList<Integer> child = new LinkedList<Integer>();


        boolean start = (Math.random() > 0.5);


        int i = 0;
        while (child.size() < P1.size()) {

            if (start) {

                if (!(child.contains(P1.get(i)))) {
                    child.add(P1.get(i));
                } else if (!(child.contains(P2.get(i)))) {
                    child.add(P2.get(i));
                }

            } else {
             //when we prefer second over first in alternate cycles
                if ((!child.contains(P2.get(i)))) {
                    child.add(P2.get(i));
                } else if (!(child.contains(P1.get(i)))) {
                    child.add(P1.get(i));
                }
            }
            //Change to other tour.
            start = !start;
            i++;
            if (i == P1.size()) {
                i = 0;
            }
        }
        return child;
    }

    // Evolution of population...
    public LinkedList<Integer>[] evolvePopulation(){
        //
        LinkedList<Integer>[] newPop = new LinkedList[worldSize];
        newPop[0] = getFittest(currWorld);
        for (int i = 1; i < worldSize; i++) {
            Random r = new Random();
            int p1 = r.nextInt(worldSize);
            int p2= r.nextInt(worldSize);
            LinkedList<Integer> parent1 = currWorld[p1];
            LinkedList<Integer> parent2= currWorld[p2];
            LinkedList<Integer> child = null;
            if(cchoice == 1)
                child = myCrossover(parent1, parent2);
            if(cchoice == 2)
                child = cyclicCrossover(parent1, parent2);
            else child = parent1;
            newPop[i] = child;
        }
        for (int i = 1; i < worldSize; i++) {
            mutate(newPop[i]);
        }
        return newPop;

    }

    public LinkedList<Integer> myCrossover(LinkedList<Integer> p1, LinkedList<Integer> p2){
//        System.out.println("Crossover 1");
        LinkedList<Integer> child = new LinkedList<Integer>();
        for(int i = 0; i < optimizer.num_of_cities; i++)
            child.add(-1);
        Random r = new Random();
        int startPos = r.nextInt(optimizer.num_of_cities);
        int endPos = r.nextInt(optimizer.num_of_cities);
        while(endPos == startPos){
            endPos = r.nextInt(optimizer.num_of_cities);
        }
        if(startPos > endPos){
        int tmp = startPos;
        startPos = endPos;
        endPos = tmp;
        }
        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < optimizer.num_of_cities; i++) {
            // If our start position is less than the end position
            if ( i > startPos && i < endPos) {
                child.remove(i);
                child.add(i, p1.get(i));
            }
             // If our start position is larger
            else  {
                if (!(i < startPos && i > endPos)) {
                    child.remove(i);
                    child.add(i, p1.get(i));
                }
            }

        }

        // Loop through parent2's city tour
        for (int i = 0; i < optimizer.num_of_cities; i++) {
            // If child doesn't have the city add it
            if (!child.contains(p2.get(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < optimizer.num_of_cities; ii++) {
                    // Spare position found, add city
                    if (child.get(ii) == -1) {
                        child.remove(ii);
                        child.add(ii, p2.get(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    private void mutate(LinkedList<Integer> tour) {
        // Loop through tour cities
        for(int tourPos1=0; tourPos1 < optimizer.num_of_cities; tourPos1++){
            // Apply mutation rate
            Random r = new Random();
            if(Math.abs(r.nextDouble()) <= mutateRate){
                int tourPos2 = r.nextInt(optimizer.num_of_cities);
                Collections.swap(tour, tourPos1, tourPos2);
            }
        }
    }

    public void run(){
        int n = 0;
//        int interval = 5;
        while (n  < 5*interval){
//            System.out.println(n);
            currWorld = evolvePopulation();
            if(n%interval == 0){
                Iterator<Integer> iter = getFittest(currWorld).iterator();
                System.out.println();
                while(iter.hasNext()){
                    int k = iter.next();
                    System.out.print(k + " ");
                }
                System.out.println("Path Length ="+calcPath(getFittest(currWorld)));
            }
            n++;
        }

        Iterator<Integer> iter = getFittest(currWorld).iterator();
        System.out.println();
        while(iter.hasNext()){
            int k = iter.next();
            System.out.print(k + " ");
        }
        System.out.println();
        System.out.println("Path Length ="+calcPath(getFittest(currWorld)));

    }
}


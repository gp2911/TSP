import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: GP
 * Date: 01/10/13
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class TSP_Optimizer extends Thread{
    public float time;
    BufferedReader br;
    public int num_of_cities = 0;
    public double currentBest;
    LinkedList<Integer> bestPath;
    double[][] edgeLengths;

    myGeneticAlgo tsp_ga;
    public double[][] getEdgeLengths(){
        return edgeLengths;
    }
    public void run(){

        br = new BufferedReader(new InputStreamReader(System.in));
        num_of_cities = 0;
        currentBest = 999999999;
        try{
            System.out.println("num of cities?");
            num_of_cities = Integer.parseInt(br.readLine());
        }
        catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
        }


        edgeLengths = new double[num_of_cities][num_of_cities];

    Random random = new Random();

    for( int i = 0; i < num_of_cities ; i++){
        for(int j = i; j < num_of_cities;j++){
            if(j==i){
                edgeLengths[i][j] = 0;
            }
            else{
                float d = random.nextFloat();
                edgeLengths[i][j] = 10*d;
                edgeLengths[j][i] = 10*d;
            }
        }
    }

    //printing out the distance matrix
    System.out.println("Generated distance matrix: ");
    for(int i = 0; i < num_of_cities; i++){
        for(int j = 0; j < num_of_cities; j++)
            System.out.printf("%8.7f    ", edgeLengths[i][j]);
        System.out.println();
    }

        GreedyHeuristic gh[] = new GreedyHeuristic[num_of_cities/2];
        synchronized (this){
        for(int i = 0; i < num_of_cities/2; i++)
            gh[i] = new GreedyHeuristic(this);
        }

        synchronized (this){
            for(int i = 0; i < num_of_cities/2; i++) {

                LinkedList<Integer> newPath =  gh[i].run();
                myGeneticAlgo g = new myGeneticAlgo(this, false);
                double newLen = g.calcPath(newPath);
                if(newLen < currentBest){
                    currentBest = newLen;
                    bestPath = newPath;
                    Iterator<Integer> iter = bestPath.iterator();
                    System.out.println();
                    while(iter.hasNext()){
                        int k = iter.next();
                        System.out.print(k + " ");
                    }
                }
                try{
                sleep(10);
                }
                catch (Exception e){}
            }

        }
        synchronized (System.out){
            Iterator<Integer> iter = bestPath.iterator();
            System.out.println();
            while(iter.hasNext()){
                int k = iter.next();
                System.out.print(k + " ");
            }
        }
        tsp_ga = new myGeneticAlgo(this, true);
        tsp_ga.start();

    }


}


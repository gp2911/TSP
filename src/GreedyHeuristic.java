import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: GP
 * Date: 09/10/13
 * Time: 5:48 PM
 * To change this template use File | Settings | File Templates.
 **/


public class GreedyHeuristic {
    TSP_Optimizer optimizer;

    public GreedyHeuristic(TSP_Optimizer optimizer1){
        optimizer = optimizer1;
    }

    public LinkedList<Integer> run(){
        Random r = new Random();
        Integer startCity = 1+r.nextInt(optimizer.num_of_cities);
        double currLength = 0;
        LinkedList<Integer> currPath = new LinkedList<Integer>();
        ArrayList<Integer> closed = new ArrayList<Integer>();
        ArrayList<Integer> open = new ArrayList<Integer>();
        int currCity = startCity;
        for (int i =1; i <=optimizer.num_of_cities; i++){
            open.add(i);
        }
        open.remove((Object) startCity);
        closed.add(startCity);
        currPath.add(startCity);

        while(! open.isEmpty()){
            int index = -1;
            double currMin = 99999;
            Iterator<Integer> myIter = open.iterator();
            while(myIter.hasNext()){
                int x= myIter.next();
                if(optimizer.edgeLengths[currCity-1][x-1] < currMin){
                    currMin = optimizer.edgeLengths[currCity-1][x-1];
                    index = x;
                }
            }
            if(index == -1)
                System.out.println("Some problem...");
            open.remove((Object) index);
            closed.add(index);
            currPath.add((Integer) index);
            currCity = index;
            currLength = currLength + currMin;
        }


        synchronized (optimizer){
            if(currLength < optimizer.currentBest){
                optimizer.currentBest = currLength;
                optimizer.bestPath = currPath;
            }

        }

        return currPath;
    }
}

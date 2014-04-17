import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: GP
 * Date: 02/10/13
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class TSP {
    public static void main(String[] args){
        TSP_Optimizer tsp = new TSP_Optimizer();
        tsp.start();
    }
}

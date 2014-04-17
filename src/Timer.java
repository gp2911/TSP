/**
 * Created with IntelliJ IDEA.
 * User: GP
 * Date: 02/10/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */

public class Timer implements Runnable{
    TSP_Optimizer q;
    int upperBound;
//    Thread thread;
    public Timer(TSP_Optimizer  q1, int i){
        q=q1;
        upperBound = i;
//        this.thread = thread;
    }
    public Timer(int i){
        upperBound = i;
        q = new TSP_Optimizer();
//        this.thread = thread;
    }


    public void run(){
        //increment q.time

        q.time++;
//        if(q.time%1000 == 0)
//            System.err.println("time:"+q.time/1000);
//        System.out.println("Exiting...");
//        if(q.time == 10000) {
//            synchronized (q){
//                System.out.println("Best distance from ACO : "+q.currentBest);
//                for(int i = 0; i < q.num_of_ants; i++){
//                    q.ants[i].interrupt();
//                }
//            }
//            q.p = new PerturbSearch(q);
//            q.p.start();
//            Timer t = new Timer(q,upperBound-10);
//            ScheduledExecutorService timerSchedule = Executors.newScheduledThreadPool(1);
//            final ScheduledFuture<?> timerHandle;
//            timerHandle = timerSchedule.scheduleAtFixedRate(t, 1,1, TimeUnit.MILLISECONDS);
//            q.timer = t;
//        }
        if (q.time > 1000*upperBound){
//            System.out.println("Exiting...");
//            System.out.println("Solution found in "+q.timestamp+" millisec");
//            System.out.println("Shortest path : "+q.bestPath);
//            System.out.println("Shortest distance : "+q.currentBest);
                synchronized (System.out){
//                System.out.println(q.time+" : "+q.currentBest +" : "+q.timestamp);
//                System.out.println(q.bestPath);
                System.exit(0);

            }
        }


    }

    public void resetTime(){
        q.time=0;
    }
}


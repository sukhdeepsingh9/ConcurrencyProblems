package problem;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class LearningCyclicBarrier {

    class Task implements Runnable{
        CyclicBarrier barrier;
        Task(CyclicBarrier barrier){
            this.barrier=barrier;
        }
        @Override
        public void run() {
            System.out.println("Entered: "+Thread.currentThread().getName());
            try {
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Barrier Broken: "+Thread.currentThread().getName());
        }
    }

    @Test
    public void test(){
        CyclicBarrier barrier=new CyclicBarrier(2, ()-> System.out.println("Barrier broken, proceed with tasks"));
        new Thread(new Task(barrier)).start();
        new Thread(new Task(barrier)).start();

    }
}

package problem;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class LearningCountDownLatch {

    class Worker extends Thread{
        CountDownLatch latch;
        Worker(CountDownLatch latch){
            this.latch=latch;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Finishing Worker: "+Thread.currentThread().getName());
            latch.countDown();
        }
    }
    class Master extends Thread{
        @Override
        public void run() {
            System.out.println("Executing master");
        }
    }


    @Test
    void test() throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(2);
        Worker A=new Worker(latch);
        Worker B=new Worker(latch);
        Master C=new Master();
        A.start();B.start();
        latch.await();
        C.start();
    }

}

public class ProducerConsumer {
    public static void main(String[] args) {
        SharedArray sharedArray = new SharedArray(100000);

        ProducerThread producerThread = new ProducerThread(sharedArray, 100);
        ConsumerThread consumerThread = new ConsumerThread(sharedArray, 100);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
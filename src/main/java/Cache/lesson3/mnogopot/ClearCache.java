package Cache.lesson3.mnogopot;

public class ClearCache  {
    public ClearCache() {

        Runnable task = () ->{
            System.out.println("Clearing Cache");
        };
        Thread thread = new Thread(task);
        thread.start();
    }



}

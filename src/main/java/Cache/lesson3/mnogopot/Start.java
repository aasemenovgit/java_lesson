package Cache.lesson3.mnogopot;

public class Start {
    public static void main(String[] args) throws InterruptedException {
        //System.out.println("test");

        Fractionable fr= new Fraction(1,2);
       // Fractionable num = Utils.cache(fr);
        fr = Utils.cache(fr);
       // System.out.println(fr.doubleValue());
       // fr.setDenum(8);
        fr.setDenum(4);
      //  fr.setNum(9);
        System.out.println(fr.doubleValue());
        System.out.println(fr.doubleValue());
        fr.setDenum(5);
        System.out.println(fr.doubleValue());
        System.out.println(fr.doubleValue());
        fr.setDenum(4);
        System.out.println(fr.doubleValue());
        System.out.println(fr.doubleValue());
       // Thread.sleep(2000);
         fr.setNum(8);
       System.out.println(fr.doubleValue());
        System.out.println(fr.doubleValue());
         Thread.sleep(4000);

        fr.setNum(8);
        System.out.println(fr.doubleValue());
        System.out.println(fr.doubleValue());


    }
}

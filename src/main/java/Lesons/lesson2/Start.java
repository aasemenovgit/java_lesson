package Lesons.lesson2;

public class Start {
    public static void main(String[] args) {
        //System.out.println("test");

        Fractionable fr= new Fraction(1,2);
       // Fractionable num = Utils.cache(fr);
        fr = Utils.cache(fr);
        System.out.println(fr.doubleValue());
        System.out.println(fr.doubleValue());
        fr.setDenum(5);
        System.out.println(fr.doubleValue());
        System.out.println(fr.doubleValue());



    }
}

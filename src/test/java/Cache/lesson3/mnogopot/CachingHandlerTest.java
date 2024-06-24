package Cache.lesson3.mnogopot;




import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

import javax.management.InvalidApplicationException;

import static org.junit.jupiter.api.Assertions.*;
class Fraction2 implements CachingHandlerTest.Fractionable2 {
    int num;
    int denum;
    int count = 0;


    Fraction2(int num, int denum) {
        this.num = num;
        this.denum = denum;

    }

    @Cache(vltime = 1000)
    public double doubleValue()
    { double res=0;
        count++;
        res= (double) num /denum;

        return (double) res;
    }
    @Cache(vltime = 0L)
    public double doubleValue2()
    { double res=0;
        count++;
        res= num/denum;

        return (double) res;
    }

    @Mutator
    public void setNum(int num) {
        this.num = num;
    }

    @Mutator
    public void setDenum(int denum) {

        this.denum = denum;
    }

    public int getCount() {
        return count;
    }
}

class CachingHandlerTest {


    /////

           @Test
        void testType() {
            Fraction2 fr = new Fraction2(1,2);
            Fractionable frs = Utils.cache(fr);
            Assertions.assertInstanceOf(Double.class,frs.doubleValue());

        }

        @Test
        void TestCount() throws InvalidApplicationException, InterruptedException {
            Fraction2 fr = new Fraction2(1,2);
            Fractionable frs = Utils.cache(fr);
            frs.setDenum(4);
            frs.doubleValue();
            frs.doubleValue();
            frs.setDenum(5);
            frs.doubleValue();
            frs.doubleValue();
            frs.setDenum(4);
            frs.doubleValue();
            frs.doubleValue();
            frs.setNum(8);
            frs.doubleValue();
            frs.doubleValue();
            Thread.sleep(4000);
            frs.setDenum(5);
            frs.doubleValue();
            frs.doubleValue();
            frs.setDenum(4);
            frs.setNum(8);
            Thread.sleep(4000);
            frs.doubleValue();
            frs.doubleValue();
            Assertions.assertEquals(fr.getCount(),5);

/* смотрим сколько раз он обращался к doubleValue()
*
invoke double value
0.25
0.25
invoke double value
0.2
0.2
0.25
0.25
invoke double value
2.0
2.0
invoke double value
1.6
1.6
invoke double value
2.0
2.0
* */




        }
    public interface Fractionable2 extends Fractionable{
        double doubleValue2();


    }
    @Test
    void TestCount_0L() throws InvalidApplicationException, InterruptedException {
        Fraction2 fr = new Fraction2(1,2);
        Fractionable2 frs = Utils.cache(fr);
        frs.setDenum(4);
        frs.doubleValue2();
        frs.doubleValue2();
        frs.setDenum(5);
        frs.doubleValue2();
        frs.doubleValue2();
        frs.setDenum(4);
        frs.doubleValue2();
        frs.doubleValue2();
        frs.setNum(8);
        frs.doubleValue2();
        frs.doubleValue2();
        Thread.sleep(4000);
        frs.setDenum(5);
        frs.doubleValue2();
        frs.doubleValue2();
        frs.setDenum(4);
        frs.setNum(8);
        Thread.sleep(4000);
        frs.doubleValue2();
        frs.doubleValue2();
        assertEquals(fr.getCount(),4);

/* смотрим сколько раз он обращался к doubleValue()

* */




    }

}
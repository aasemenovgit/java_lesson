package Lesons.lesson2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.management.InvalidApplicationException;

class Fraction2 implements Fractionable{
    int num;
    int denum;
    int count = 0;

    Fraction2(int num, int denum) {
        this.num = num;
        this.denum = denum;

    }

    @Cache
    public double doubleValue()
    {
       count++;
       return (double) num/denum;
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


    @Test
    void testType() {
        Fraction fr = new Fraction(5,9);
        Fractionable frs = Utils.cache(fr);
        //String nn ="Проверка типа";
        Assertions.assertInstanceOf(Double.class,fr.doubleValue());
       // Assertions.assertInstanceOf(Double.class,nn);
    }

    @Test
    void TestCount() throws InvalidApplicationException {
        Fraction2 fr = new Fraction2(5,9);
        Fractionable frs = Utils.cache(fr);
        frs.doubleValue();
        frs.doubleValue();
        frs.doubleValue();
        frs.setDenum(5);
        frs.doubleValue();
        frs.doubleValue();
        frs.doubleValue();
        frs.setNum(4);
        frs.doubleValue();
        frs.doubleValue();
        frs.doubleValue();
        Assertions.assertEquals(fr.getCount(),3);

    }
}
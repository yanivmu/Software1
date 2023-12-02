package il.ac.tau.cs.sw1.ex9.riddles.second;
import java.util.Random;

public class B2 extends A2{
    public boolean randomBoolean;

    public B2(Boolean randomBoolean){
        this.randomBoolean = randomBoolean;

    }
    public B2(){
        this.randomBoolean = true;

    }
    public B2 getA(Boolean randomBoolean){
        return new B2(randomBoolean);
    }

    public String foo(String str){
        if(this.randomBoolean) {
            return str.toUpperCase();
        }
        else {
            return str.toLowerCase();

        }
    }
}

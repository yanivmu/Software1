package il.ac.tau.cs.sw1.ex9.riddles.third;

public class B3 extends A3{
    public String text;
    public B3(String str){
        super(str);
        this.text = str;

    }
    @Override
    public void foo(String str) throws B3 {
        if (str.equals(this.text)) {
            throw new B3(this.text);
        }
    }

    public String getMessage() {
        return this.text;
    }

}
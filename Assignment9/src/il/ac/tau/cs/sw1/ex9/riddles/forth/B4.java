package il.ac.tau.cs.sw1.ex9.riddles.forth;

import java.util.List;
import java.util.Arrays;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class B4 implements Iterator<String> {
    private static int cnt;
    private static int j;
    private static String str;
    private List<String> lst;
    private Iterator<String> iter;
    private static String[] stringArray;


    public B4(String[] strs, int j){
        this.cnt = 0;
        this.j = j;
        this.stringArray =strs;
        lst = Arrays.asList(strs);
        this.iter = lst.iterator();

    }

    @Override
    public boolean hasNext() {
        if (cnt == 0){
            return iter.hasNext();
        }
        else if (this.lst.size() * j > cnt){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String next() {
        str = lst.get(cnt % this.lst.size());
        cnt++;
        return str;

    }

}
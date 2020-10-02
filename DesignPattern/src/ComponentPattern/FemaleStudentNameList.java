package ComponentPattern;

import IteratorPattern.FemaleStudentNameListIterator;
import IteratorPattern.Iterator;

public class FemaleStudentNameList {
    private String[] names;
    public FemaleStudentNameList() {
        this.names = new String[] {"John", "Peter", "Harry", "Ron", "Jack", "Clark"};
    }
//    public String[] getNames() {
//        return this.names;
//    }
    public void print() {
        System.out.println("---------------Female student names-----------------");
        for(int i = 0; i < names.length; i++)
            System.out.println(names[i]);
    }
    public Iterator createIterator() {
        return new FemaleStudentNameListIterator(names);
    }
}

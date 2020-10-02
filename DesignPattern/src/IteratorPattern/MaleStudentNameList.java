package IteratorPattern;

import java.util.ArrayList;

public class MaleStudentNameList {
    private ArrayList names;
    public MaleStudentNameList() {
        this.names = new ArrayList();
        this.names.add("Mary");
        this.names.add("Victoria");
        this.names.add("Terry");
    }
//    public ArrayList getNames() {
//        return this.names;
//    }
    public void print() {
        System.out.println("---------------Male student names-----------------");
        for(int i = 0; i < names.size(); i++)
            System.out.println(names.get(i));
    }
    public Iterator createIterator() {
        return new MaleStudentNameListIterator(this.names);
    }
}

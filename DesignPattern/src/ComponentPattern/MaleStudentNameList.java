package ComponentPattern;

import IteratorPattern.Iterator;
import IteratorPattern.MaleStudentNameListIterator;

import java.util.ArrayList;

public class MaleStudentNameList extends NameListComponent {
    private String description = "Male Student Name List";
    private ArrayList children = new ArrayList();
    private ArrayList names;
    @Override
    public void getDescription() {
        System.out.println(description);
    }

    @Override
    public void add(NameListComponent component) {
        children.add(component);
    }

    public void print(NameListComponent component) {

    }

    @Override
    public NameListComponent getChild(int i) {
        return (NameListComponent) this.children.get(i);
    }

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

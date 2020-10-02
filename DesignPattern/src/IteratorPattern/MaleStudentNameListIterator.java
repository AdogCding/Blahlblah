package IteratorPattern;

import java.util.ArrayList;

public class MaleStudentNameListIterator implements Iterator {
    private ArrayList names;
    private int position = 0;
    public MaleStudentNameListIterator(ArrayList list) {
        this.names = list;
    }
    @Override
    public boolean hasNext() {
        if (position >= names.size())
            return false;
        else
            return true;
    }

    @Override
    public Object next() {
        if (hasNext()) {
            Object item = names.get(position);
            position++;
            return item;
        }
        return null;
    }
}

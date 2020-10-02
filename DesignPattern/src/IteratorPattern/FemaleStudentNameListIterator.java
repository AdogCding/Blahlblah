package IteratorPattern;

public class FemaleStudentNameListIterator implements Iterator {
    private String[] names;
    private int position = 0;
    public FemaleStudentNameListIterator(String[] names) {
        this.names = names;
    }

    @Override
    public boolean hasNext() {
        return !(position >= names.length);
    }

    @Override
    public Object next() {
        if (hasNext()) {
            Object item = names[position];
            position ++;
            return item;
        }
        return null;
    }
}

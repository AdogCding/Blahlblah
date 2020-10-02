package ComponentPattern;

import java.util.ArrayList;
// don't want to continue this.
// shortly, very component would extend this class and use recursion.
public abstract class NameListComponent {
    public void getDescription() {
        throw new java.lang.UnsupportedOperationException();
    }
    public void add(NameListComponent component) {
        throw  new java.lang.UnsupportedOperationException();
    }
    public void print() {
        throw new java.lang.UnsupportedOperationException();
    }
    public NameListComponent getChild(int i) {
        throw new java.lang.UnsupportedOperationException();
    }
}

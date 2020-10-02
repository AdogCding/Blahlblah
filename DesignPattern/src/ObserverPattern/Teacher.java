package ObserverPattern;

import java.util.ArrayList;

public class Teacher implements Subject {
    ArrayList<Observer> observerList;
    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int idx = observerList.indexOf(observer);
        if (idx >= 0)
            observerList.remove(idx);
    }

    @Override
    public void setChanged() {

    }

    @Override
    public void notifyObserver() {
        for(Observer observer: observerList)
            observer.update();
    }
}

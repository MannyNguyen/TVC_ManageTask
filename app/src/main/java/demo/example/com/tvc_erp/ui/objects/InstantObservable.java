package demo.example.com.tvc_erp.ui.objects;

import java.util.Observable;

public class InstantObservable extends Observable {
    public void addObserver(java.util.Observer observer, boolean instant) {
        super.addObserver(observer);
        if (instant && observer != null) {
            observer.update(this, null);
        }
    }
}

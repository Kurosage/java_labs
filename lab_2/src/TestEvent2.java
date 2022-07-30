/* Уведомление*/
import java.util.*;
class beWatched extends Observable {//Класс наблюдаемого объекта
    void notifyObs() {
        setChanged();
        notifyObservers(new Integer(55));
    }
}
class Watcher implements Observer {//Класс обозревателя
    public void update(Observable obs, Object arg) {
        System.out.println ("received " + ((Integer)arg).intValue());
    }
}
class TestEvent2 {
    public static void main (String[] args) {
        Watcher w = new Watcher();//Создать объект приемника
        beWatched bW = new beWatched();//Создать объект источника
        bW.addObserver(w);
        bW.notifyObs();//Уведомить
    }
}

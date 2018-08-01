package ru.squel.babynote.eating;

/**
 * Created by sq on 30.08.2017.
 */
public interface EatingViewPresenterContract {

    public interface View {
        public void displayEatingHeaderMilk(int amount);
        public void displayEatingHeaderMilk(String name);
    }

    public interface Presenter {
        public void parseBundle(String name, int amount);
    }
}

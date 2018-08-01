package ru.squel.babynote;

/**
 * Created by sq on 01.09.2017.
 */
public interface RecyclerItemClickListenerCallback {

    void editButtonCallback(final int position);
    void deleteButtonCallback(final int position);

}

package ru.squel.babynote.data.baseData;

import java.util.HashMap;

/**
 * Created by sq on 03.09.2017.
 * Класс для отображения диалога редактирования, отображаемых записей,
 * предоставляет возможность получить данные для заполнения формы диалога, и её сохранения.
 */
public abstract class DataBaseEntry {

    private long id = 0;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public abstract HashMap<String, String> getParams();
    public abstract void setParams(HashMap<String, String> params);
    public abstract String[] getTypesMap();

    //получение даты начала для сортировки
    public abstract long getDateTimeBegin();
}

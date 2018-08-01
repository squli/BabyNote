package ru.squel.babynote.data.baseData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import ru.squel.babynote.data.Eat;

/**
 * Created by sq on 20.11.2017.
 * Класс для мгновенных действий, которые не имеют продолжительности, например памперсы и лекарства.
 */

public abstract class InstantlyActionData extends DataBaseEntry {

    // заголовки столбцов в таблицах
    public interface FieldNames {
        String idFieldName = "_id";
        String dateTimeFieldName = "dateTimeBegin";
        String typeFieldName = "type";
    }

    // данные
    private int type;
    private long dateTime = new Date().getTime();

    // пустой конструктор
    public InstantlyActionData() {

    }

    // базовый конструктор
    public InstantlyActionData(final int type, long dateTime) {
        this.type = type;
        this.dateTime = dateTime;
    }

    // сеттеры-геттеры
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
    public long getDateTimeBegin() { return dateTime; }
    public void setDateTimeBegin(long dateTime) { this.dateTime = dateTime; }

    @Override
    public HashMap<String, String> getParams() {
        HashMap<String, String> res = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        res.put(FieldNames.dateTimeFieldName, formatter.format(getDateTimeBegin()));
        res.put(FieldNames.typeFieldName, String.valueOf(type));
        return res;
    }

    @Override
    public void setParams(HashMap<String, String> params) {
        this.setId(Long.parseLong(params.get(Eat.FieldNames.idFieldName)));
        this.type = Integer.parseInt(params.get(Eat.FieldNames.typeFieldName));
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date dateBegin = new Date();
        try {
            dateBegin = formatter.parse(params.get(FieldNames.dateTimeFieldName));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.dateTime = dateBegin.getTime();
    }

    @Override
    public String[] getTypesMap() {
        return new String[0];
    }
}

package ru.squel.babynote.data.baseData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by sq on 29.10.2017.
 * Базовый класс для всех типов данных, которые состоят из даты начала, даты окончания и типа
 * действия. От него наследуются сон, купание, прогулка, бодрствование. Лекарства и памперсы не
 * наследуются т.к. не имеют даты окончания - выполняются мгновенно.
 */

public abstract class ShortActionData extends InstantlyActionData {

    // заголовки столбцов в таблицах
    public interface FieldNames {
        String idFieldName = InstantlyActionData.FieldNames.idFieldName;
        String dateTimeBeginFieldName = InstantlyActionData.FieldNames.dateTimeFieldName;
        String typeFieldName = InstantlyActionData.FieldNames.typeFieldName;
        String dateTimeEndFieldName = "dateTimeEnd";
    }

    // данные
    //private long dateTimeBegin;
    private long dateTimeEnd;
   // private int type = 0;

    // пустой конструктор
    public ShortActionData() {}

    // базовый конструктор
    public ShortActionData(final long dateTimeBegin, final long dateTimeEnd, int type) {
        //this.dateTimeBegin = dateTimeBegin;
        super(type, dateTimeBegin);
        this.dateTimeEnd = dateTimeEnd;
        //this.type = type;
    }

    // сеттеры-геттеры
    //public void setDateTimeBegin(Date dateTimeBegin) { this.dateTimeBegin = dateTimeBegin.getTime(); }
    public void setDateTimeEnd(Date dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd.getTime();
    }
    //public long getDateTimeBegin() { return dateTimeBegin; }
    public long getDateTimeEnd() {
        return dateTimeEnd;
    }
    //public int getType() { return type; }
    //public void setType(int type) { this.type = type; }

    @Override
    public HashMap<String, String> getParams() {
        HashMap<String, String> res = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        res.put(ShortActionData.FieldNames.dateTimeBeginFieldName, formatter.format(getDateTimeBegin()));
        res.put(ShortActionData.FieldNames.dateTimeEndFieldName, formatter.format(getDateTimeEnd()));
        res.put(ShortActionData.FieldNames.typeFieldName, String.valueOf(super.getType()));
        return res;
    }

    @Override
    public void setParams(HashMap<String, String> params) {
        this.setId(Long.parseLong(params.get(ShortActionData.FieldNames.idFieldName)));
        super.setType(Integer.parseInt(params.get(ShortActionData.FieldNames.typeFieldName)));

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date dateBegin = new Date();
        Date dateEnd = new Date();
        try {
            dateBegin = formatter.parse(params.get(ShortActionData.FieldNames.dateTimeBeginFieldName));
            dateEnd = formatter.parse(params.get(ShortActionData.FieldNames.dateTimeBeginFieldName));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.setDateTimeBegin(dateBegin.getTime());
        this.dateTimeEnd = dateEnd.getTime();
    }
}

package ru.squel.babynote.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import ru.squel.babynote.data.baseData.DataBaseEntry;

/**
 * Created by sq on 24.08.2017.
 */
public class Eat extends DataBaseEntry {

    public static HashMap<String, Integer> eatTypes = new HashMap<>();

    static {
        eatTypes.put("Milk Left", 1);
        eatTypes.put("Milk Right", 2);
        eatTypes.put("Milk Bottle", 3);
        eatTypes.put("Mixture Bottle", 4);
        eatTypes.put("Food Cabachok", 5);
        eatTypes.put("Food Meat", 5);
    }

    public interface FieldNames {
        String dateTimeBeginFieldName = "dateTimeBegin";
        String dateTimeEndFieldName = "dateTimeEnd";
        String typeFieldName = "type";
        String amountFieldName = "amount";
        String idFieldName = "_id";
    }

    private long dateTimeBegin = new Date().getTime();
    private long dateTimeEnd = new Date().getTime();
    private int type = 0;
    private int amount = 0;

    public Eat() {

    }

    public Eat(int type) {
        this.type = type;
    }

    public Eat(int type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public void setDateTimeBegin(Date dateTimeBegin) {
        this.dateTimeBegin = dateTimeBegin.getTime();
    }

    public void setDateTimeEnd(Date dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd.getTime();
    }

    public long getDateTimeBegin() {
        return dateTimeBegin;
    }

    public long getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * удалить потом и переделать
     * @return
     */
    public String getDescription() {
        String str = "";

        if (this.type == eatTypes.get("Milk Left")) {
            str = "Milk Left";
        }
        else if (this.type == eatTypes.get("Milk Right")) {
            str = "Milk Right";
        }
        else if (this.type == eatTypes.get("Milk Bottle")) {
            str = "Bottle " + String.valueOf(this.amount);
        }

        return str;
    }

    /**
     * Формирование карты из пар названия параметра - значение параметра для отображения
     * в диалоге редактирования записи
     * @return
     */
    @Override
    public HashMap<String, String> getParams() {
        HashMap<String, String> res = new HashMap<>();

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        res.put(FieldNames.dateTimeBeginFieldName, formatter.format(getDateTimeBegin()));
        res.put(FieldNames.dateTimeEndFieldName, formatter.format(getDateTimeEnd()));
        res.put(FieldNames.typeFieldName, String.valueOf(type));
        res.put(FieldNames.amountFieldName, String.valueOf(amount));

        return res;
    }

    /**
     * Обновление записи в базе значениями из дилога редактирования
     * @param params
     */
    @Override
    public void setParams(HashMap<String, String> params) {
        this.setId(Long.parseLong(params.get(FieldNames.idFieldName)));
        this.type = Integer.parseInt(params.get(FieldNames.typeFieldName));
        this.amount = Integer.parseInt(params.get(FieldNames.amountFieldName));
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date dateBegin = new Date();
        Date dateEnd = new Date();
        try {
            dateBegin = formatter.parse(params.get(FieldNames.dateTimeBeginFieldName));
            dateEnd = formatter.parse(params.get(FieldNames.dateTimeBeginFieldName));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.dateTimeBegin = dateBegin.getTime();
        this.dateTimeEnd = dateEnd.getTime();
    }

    @Override
    public String[] getTypesMap() {
        String res[] = new String[eatTypes.size()];
        int i = 0;

        for (String s: eatTypes.keySet()) {
            res[i] = s;
            i += 1;
        }
        return res;
    }
}

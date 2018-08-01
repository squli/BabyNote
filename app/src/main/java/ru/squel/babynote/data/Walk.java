package ru.squel.babynote.data;

import java.util.HashMap;

import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.data.baseData.ShortActionData;

/**
 * Created by sq on 17.10.2017.
 */

public class Walk extends ShortActionData {

    public static HashMap<String, Integer> walkTypes = new HashMap<>();
    public static final String onStreet = "onStreet";
    public static final String onBalcony = "onBalcony";

    static {
        walkTypes.put(onStreet, 0);
        walkTypes.put(onBalcony, 1);
    }

    public Walk() {    }

    public Walk(final long dateTimeBegin, final long dateTimeEnd, int type) {
        super(dateTimeBegin, dateTimeEnd, type);
    }

    public Walk(final DataBaseEntry db) {
        setParams(db.getParams());
    }

    @Override
    public String[] getTypesMap() {
        String res[] = new String[walkTypes.size()];
        int i = 0;

        for (String s: walkTypes.keySet()) {
            res[i] = s;
            i += 1;
        }
        return res;
    }
}

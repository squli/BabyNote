package ru.squel.babynote.data;

import java.util.HashMap;

import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.data.baseData.ShortActionData;

/**
 * Created by sq on 17.10.2017.
 */

public class Swim extends ShortActionData {

    public static HashMap<String, Integer> swimTypes = new HashMap<>();
    public static final String withSoap = "withSoap";
    public static final String withSalt = "withSalt";

    static {
        swimTypes.put(withSoap, 0);
        swimTypes.put(withSalt, 1);
    }

    public Swim() {    }

    public Swim(final long dateTimeBegin, final long dateTimeEnd, int type) {
        super(dateTimeBegin, dateTimeEnd, type);
    }

    public Swim(final DataBaseEntry db) {
        setParams(db.getParams());
    }

    @Override
    public String[] getTypesMap() {
        String res[] = new String[swimTypes.size()];
        int i = 0;

        for (String s: swimTypes.keySet()) {
            res[i] = s;
            i += 1;
        }
        return res;
    }

}

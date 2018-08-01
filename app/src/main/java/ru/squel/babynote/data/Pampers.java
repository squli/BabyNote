package ru.squel.babynote.data;

import java.util.HashMap;

import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.data.baseData.InstantlyActionData;

/**
 * Created by sq on 24.08.2017.
 * POJO для обработки смены подгузника
 */
public class Pampers extends InstantlyActionData {

    public static HashMap<String, Integer> pampersTypes = new HashMap<>();

    public static final String pampersSmallType = "pampersSmallType";
    public static final String pampersLargeType = "pampersLargeType";
    public static final String pampersSmallAndLargeType = "pampersSmallAndLargeType";

    static {
        pampersTypes.put(pampersSmallType, 0);
        pampersTypes.put(pampersLargeType, 1);
        pampersTypes.put(pampersSmallAndLargeType, 2);
    }

    public Pampers() {    }

    public Pampers(final int type, long dateTime) {
        super(type, dateTime);
    }

    public Pampers(final DataBaseEntry db) {
        setParams(db.getParams());
    }

    @Override
    public String[] getTypesMap() {
        String res[] = new String[pampersTypes.size()];
        int i = 0;

        for (String s: pampersTypes.keySet()) {
            res[i] = s;
            i += 1;
        }
        return res;
    }
}

package ru.squel.babynote.data;

import java.util.HashMap;

import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.data.baseData.ShortActionData;

/**
 * Created by sq on 24.08.2017.
 */
public class Sleep  extends ShortActionData {

    public static HashMap<String, Integer> sleepTypes = new HashMap<>();
    public static final String lefSide = "lefSide";
    public static final String rightSide = "rightSide";
    public static final String deepSleep = "deepSleep";

    static {
        sleepTypes.put(deepSleep, 0);
        sleepTypes.put(lefSide, 1);
        sleepTypes.put(rightSide, 2);
    }

    public Sleep() {

    }

    public Sleep(final long dateTimeBegin, final long dateTimeEnd, int type) {
        super(dateTimeBegin, dateTimeEnd, type);
    }

    public Sleep(final DataBaseEntry db) {
        setParams(db.getParams());
    }

    @Override
    public String[] getTypesMap() {
        String res[] = new String[sleepTypes.size()];
        int i = 0;

        for (String s: sleepTypes.keySet()) {
            res[i] = s;
            i += 1;
        }
        return res;
    }
}

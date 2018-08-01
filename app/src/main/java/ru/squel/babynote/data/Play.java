package ru.squel.babynote.data;

import java.util.HashMap;

import ru.squel.babynote.data.baseData.DataBaseEntry;
import ru.squel.babynote.data.baseData.ShortActionData;

/**
 * Created by sq on 17.10.2017.
 */

public class Play extends ShortActionData {

    public static HashMap<String, Integer> playTypes = new HashMap<>();
    public static final String inBad = "inBad";
    public static final String inArena = "inArena";
    public static final String inAbdominal = "inAbdominal";

    static {
        playTypes.put(inBad, 0);
        playTypes.put(inArena, 1);
        playTypes.put(inAbdominal, 2);
    }

    public Play() {    }

    public Play(final long dateTimeBegin, final long dateTimeEnd, int type) {
        super(dateTimeBegin, dateTimeEnd, type);
    }

    public Play(final DataBaseEntry db) {
        setParams(db.getParams());
    }

    @Override
    public String[] getTypesMap() {
        String res[] = new String[playTypes.size()];
        int i = 0;

        for (String s: playTypes.keySet()) {
            res[i] = s;
            i += 1;
        }
        return res;
    }
}

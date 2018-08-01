package ru.squel.babynote.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sq on 24.08.2017.
 * POJO для представление ребенка, для которого ведется статистика
 */
public class Baby {

    private String babyName = "";
    private Date babyBirthday = new Date();;

    /// 0 - это мальчик
    private int babySex = 0;

    public Baby() {

    }

    public Baby(String babyName, int babySex) {
        this.babyName = babyName;
        this.babySex = babySex;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public void setBabySex(int babySex) {
        this.babySex = babySex;
    }

    public void setBabyBirthday(Date babyBirthday) {
        this.babyBirthday = babyBirthday;
    }

    public String getName() {return babyName;}
    public int getBabySex() {return babySex;}

    public String getBabyBirthday() {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(babyBirthday);
    }

}

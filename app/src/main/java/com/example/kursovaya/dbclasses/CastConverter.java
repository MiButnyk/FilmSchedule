package com.example.kursovaya.dbclasses;

import androidx.room.TypeConverter;

import com.example.kursovaya.pojo.Cast;
import com.example.kursovaya.pojo.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CastConverter {

    @TypeConverter
    public String fromCast(List<Cast> castList){
        List<String> stringList = new ArrayList<>();
        for(Cast c: castList){
            stringList.add(c.toString());
        }
        return String.join(",", stringList);
    }

    @TypeConverter
    public List<Cast> toCast(String data){
        String[] stringList = data.split(",");
        List<Cast> castList = new ArrayList<>();
        for(String s: stringList){
            String[] strarr = s.split("-");
            if(strarr.length == 2){
                castList.add(new Cast(strarr[0], strarr[1]));
            }
            else{
                castList.add(new Cast("", ""));
            }
        }
        return castList;
    }
}

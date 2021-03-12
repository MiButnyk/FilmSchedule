package com.example.kursovaya.dbclasses;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GenresConverter {

    @TypeConverter
    public String fromGenres(List<String> genres){
        return String.join(",", genres);
    }

    @TypeConverter
    public List<String> toGenres(String data){
        return Arrays.asList(data.split(","));
    }
}

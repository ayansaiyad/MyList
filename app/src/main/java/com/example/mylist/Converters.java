package com.example.mylist;
import androidx.room.TypeConverter;
import java.util.Date;
//Converts between java dates and DB primitive types because RoomDB cannot store Dates
public class Converters {
    @TypeConverter public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

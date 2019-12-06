package com.ajs.bloknot.database;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * A class of converters for Date objects in the database.
 */
public class DatabaseConverters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}

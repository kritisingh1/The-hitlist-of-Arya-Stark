package com.example.dell.aryashitlist;

import android.provider.BaseColumns;

/**
 * Created by kriti on 04-01-2017.
 */

public class TableData {

    public TableData(){

    }

    public static abstract class TableInfo implements BaseColumns{
        public static final String NAME = "name";
        public static final String STATUS = "status";
        public static final String DESCRIPTION = "description";
        public static final String IMG_LOCATION = "img_location";
        public static final String DATABASE_NAME = "user_info";
        public static final String TABLE_NAME = "reg_info";
    }

}

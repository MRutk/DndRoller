package pl.s196453.dndroller;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Profile.class}, version = 1, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase{

    private static MyAppDatabase instance;

    public static MyAppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),MyAppDatabase.class,"profiledb")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destInstance(){
        instance = null;
    }

    public abstract ProfileDAO profileDAO();
}

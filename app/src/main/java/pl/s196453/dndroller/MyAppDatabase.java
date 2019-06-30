package pl.s196453.dndroller;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

@Database(entities = {Profile.class}, version = 1, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase{

    private static MyAppDatabase instance;

    public static MyAppDatabase getInstance(Context context){
        if (instance == null){
            /*instance = Room.databaseBuilder(context.getApplicationContext(),MyAppDatabase.class,"profiledb")
                    .allowMainThreadQueries()
                    .build();*/
            instance = buildDb(context);
        }
        return instance;
    }

    private static MyAppDatabase buildDb (final Context context){
        return Room.databaseBuilder(context, MyAppDatabase.class, "profileDb")
                .addCallback(new Callback(){
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db){
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(new Runnable(){
                            @Override
                            public void run(){
                            getInstance(context).profileDAO()
                                    .insert(Profile.populateDb());
                            }
                        });
                    }
                })
                .allowMainThreadQueries()
                .build();
    }

    public static void destInstance(){
        instance = null;
    }

    public abstract ProfileDAO profileDAO();
}

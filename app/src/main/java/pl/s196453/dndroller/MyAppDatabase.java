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

    //method creating instance od db to use in fragments
    public static MyAppDatabase getInstance(Context context){
        if (instance == null){
            /*instance = Room.databaseBuilder(context.getApplicationContext(),MyAppDatabase.class,"profiledb")
                    .allowMainThreadQueries()
                    .build();*/
            instance = buildDb(context);
        }
        return instance;
    }

    //method to build db and ad a default profile through an executor so it does not take up 'main' time
    //added the default profile only once
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
                .allowMainThreadQueries() //not recommended but every call to db in project is done through a thread or a thread executor
                .build();
    }

    public static void destInstance(){
        instance = null;
    }

    public abstract ProfileDAO profileDAO();
}

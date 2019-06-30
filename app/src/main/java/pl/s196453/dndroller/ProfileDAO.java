package pl.s196453.dndroller;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ProfileDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Profile profile);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertALL(Profile... profiles);

    @Update
    public void update(Profile profile);    //not used, see confilct resolution on isert

    @Delete
    public void delete(Profile profile);    //not used, selecting profile to delete by passing name

    @Query("SELECT * FROM profiles")
    public List<Profile> getAll();

    @Query("SELECT * FROM profiles")        //did not get livedata to work with spinner, deemed regular list is enough for my use case
    LiveData<List<Profile>> getAllProfiles();

    @Query("SELECT * FROM profiles where name = :string")
    Profile getProfile(String string);

    @Query("DELETE FROM profiles WHERE name = :string")
    int deleteProfile(String string);

   /* @Query("SELECT name FROM profiles WHERE name =:string")
    String getProfileName(String string);*/

    /*@Query("SELECT name FROM profiles")
    public LiveData<List<Profile>> getNames(List<String> name);*/
}
    /*@Query (“SELECT * FROM profiles WHERE name = :name“)
    profiles fetchProfileByName (String name);*/
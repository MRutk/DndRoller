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

    @Update
    public void update(Profile profile);

    @Delete
    public void delete(Profile profile);

    @Query("SELECT * FROM profiles")
    public List<Profile> getAll();

    @Query("SELECT * FROM profiles")
    LiveData<List<Profile>> getAllProfiles();

    @Query("delete from profiles WHERE name = :string")
    int deleteProfile(String string);

   /* @Query("SELECT name FROM profiles WHERE name =:string")
    String getProfileName(String string);*/

    /*@Query("SELECT name FROM profiles")
    public LiveData<List<Profile>> getNames(List<String> name);*/
}
    /*@Query (“SELECT * FROM profiles WHERE name = :name“)
    profiles fetchProfileByName (String name);*/
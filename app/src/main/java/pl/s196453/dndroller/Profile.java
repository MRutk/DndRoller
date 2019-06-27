package pl.s196453.dndroller;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "profiles")
public class Profile {
    @PrimaryKey @NonNull
    private String name;

    @ColumnInfo(name = "Strength")
    private int strength;
    @ColumnInfo(name = "Dexterity")
    private int dexterity;
    @ColumnInfo(name = "Constitution")
    private int constitution;
    @ColumnInfo(name = "Intelligence")
    private int intelligence;
    @ColumnInfo(name = "Wisdom")
    private int wisdom;
    @ColumnInfo(name = "Charisma")
    private int charisma;
    @ColumnInfo(name = "AC")
    private int armourClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength(){
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getArmourClass() {
        return armourClass;
    }

    public void setArmourClass(int armourClass) {
        this.armourClass = armourClass;
    }


    public Profile(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int armourClass){
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.armourClass = armourClass;
    }

    public Profile(Profile profile){
        this.name = profile.getName();
        this.strength = profile.getStrength();
        this.dexterity = profile.getDexterity();
        this.constitution = profile.getConstitution();
        this.intelligence = profile.getIntelligence();
        this.wisdom = profile.getWisdom();
        this.charisma = profile.getCharisma();
        this.armourClass = profile.getArmourClass();
    }

    @Override
    public String toString(){
        return name;
    }
}

package io.github.jadedchara.ashfall.common.cca.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import io.github.jadedchara.ashfall.Ashfall;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

public class PlayerControlComponent implements AutoSyncedComponent {

    public static final ComponentKey<PlayerControlComponent> PLAYER_INFO =
            ComponentRegistry.getOrCreate(Ashfall.id("player_info"), PlayerControlComponent.class);

    //Defining values
    /*
    - Custom Name - String
    - Custom Color - 'int'
    - Headmate Names - String[]
    - Shader Effect - boolean
    - TBD

     */
    private final Player storedPlayer;

    private String customName = "";
    private int color = 0xFFFFFF;
    private HashMap<String, Integer> headmates = new HashMap<>();
    private String frontName = "";
    private int frontColor = 0xFFFFFF;
    private boolean applyGlitch = false;
    private boolean applyLoreName = false;

    public PlayerControlComponent(Player player){
        this.storedPlayer = player;
    }


    //NAME
    public void setCustomName(String name){
        this.customName = name;
        this.sync();
    }
    public String getCustomName(){
        if(this.customName.equals("")){
            return null;
        }
        return this.customName;
    }
    public void resetCustomName(){
        this.customName = "";
        this.sync();
    }

    //COLOR
    public void setColor(int c){
        this.color = c;
        this.sync();
    }
    public int getColor(){
        return this.color;
    }
    public void resetColor(){
        this.color = 0xFFFFFF;
        this.sync();
    }

    //HEADMATES
    public HashMap<String,Integer> getHeadmates(){
        return this.headmates;
    }

    public void setHeadmate(String n){
        this.frontName = n;
        this.frontColor = this.headmates.get(n);
        this.sync();
    }
    public void addHeadmate(String n, int c){
        this.headmates.put(n,c);
        this.sync();
    }

    public void modifyHeadmateName(String n, String newN){
        int t = this.headmates.get(n);
        this.headmates.remove(n);
        this.headmates.put(newN,t);
        if(this.frontName.equals(n) && this.frontColor == t){
            this.frontName = newN;
        }
        this.sync();
    }
    public void modifyHeadmateColor(String n, int newC){
        int t = this.headmates.get(n);
        this.headmates.replace(n,newC);
        if(this.frontName.equals(n) && this.frontColor == t){
            this.frontColor = newC;
        }
        this.sync();
    }
    public void modifyHeadmate(String n, String newN, int newC){
        int t = this.headmates.get(n);
        this.headmates.remove(n);
        this.headmates.put(newN, newC);
        if(this.frontName.equals(n) && this.frontColor == t){
            this.frontName = newN;
            this.frontColor = newC;
        }
        this.sync();
    }
    public void removeHeadmate(String n){
        this.headmates.remove(n);
        this.sync();
    }
    public void clearSystem(){
        this.headmates = new HashMap<>();
        this.sync();
    }

    //NBT Management
    public void sync(){
        PLAYER_INFO.sync(this.storedPlayer);
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag) {

    }

    @Override
    public void writeToNbt(CompoundTag compoundTag) {

    }
}

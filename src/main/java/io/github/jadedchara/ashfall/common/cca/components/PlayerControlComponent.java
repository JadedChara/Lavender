package io.github.jadedchara.ashfall.common.cca.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import io.github.jadedchara.ashfall.Ashfall;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.UUID;

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
    private boolean show  = false;
    private boolean applyGlitch = false;
    private boolean applyLoreName = false;
    private boolean hideName = false;
    private boolean visible = true;
    private UUID disguise;
    private boolean mimic = false;

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
    public void toggleShowFront(){
        this.show = !this.show;
        this.sync();
    }
    public boolean canShowFront(){
        return this.show;
    }
    public String getFrontName(){
        return this.frontName;
    }
    public int getFrontColor(){
        return this.frontColor;
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





    //LORE
    public void toggleLoreName(){
        this.applyLoreName = !this.applyLoreName;
        this.sync();
    }
    public void toggleLoreFX(){
        this.applyGlitch = !this.applyGlitch;
        this.sync();
    }
    public boolean hasLoreName(){
        return this.applyGlitch;
    }
    public boolean hasLoreFX(){
        return this.applyLoreName;
    }
    public void toggleConcealName(){
        this.hideName = !this.hideName;
        this.sync();
    }
    public void toggleVisible(){
        this.visible = !this.visible;
        this.sync();
    }
    public boolean isVisible() {
        return this.visible;
    }
    public boolean isAnon() {
        return this.hideName;
    }
    public void toggleMimic(){
        this.mimic = !this.mimic;
        this.sync();
    }
    public void setDisguise(UUID d){
        this.disguise = d;
        this.sync();
    }
    public boolean isDisguised() {
        if(this.disguise == null){
            return false;
        }
        return this.mimic;
    }
    public UUID getDisguise() {
        return this.disguise;
    }

    //NBT Management
    public void sync(){
        PLAYER_INFO.sync(this.storedPlayer);
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.headmates = new HashMap<>();
        CompoundTag fetch = tag.getCompound("Headmates");
        for(String n : fetch.getAllKeys()){
            this.headmates.put(n, fetch.getInt(n));
        }
        this.disguise = tag.getUUID("Disguise");
        this.visible = tag.getBoolean("Visible");
        this.hideName = tag.getBoolean("Anon");
        this.mimic = tag.getBoolean("Mimic");
        this.applyGlitch = tag.getBoolean("applyFX");
        this.applyLoreName = tag.getBoolean("applyName");
        this.customName = tag.getString("CustomName");
        this.color = tag.getInt("Color");

        this.frontName = tag.getString("FrontName");
        this.frontColor = tag.getInt("FrontColor");
        this.show = tag.getBoolean("ShowFront");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        CompoundTag batch = new CompoundTag();
        for(String n : this.headmates.keySet()){
            batch.putInt(n,this.headmates.get(n));
        }
        tag.put("Headmates",batch);
        tag.putString("FrontName",this.frontName);
        tag.putInt("FrontColor",this.frontColor);
        tag.putBoolean("ShowFront",this.show);

        tag.putInt("Color",this.color);
        tag.putString("CustomName",this.customName);
        tag.putBoolean("applyFX",this.applyGlitch);
        tag.putBoolean("applyName",this.applyLoreName);

        tag.putBoolean("Anon",this.hideName);
        tag.putBoolean("Visible",this.visible);
        tag.putBoolean("Mimic",this.mimic);
        tag.putUUID("Disguise",this.disguise);
    }
}

package io.github.jadedchara.ashfall.common.cca.components;

import net.minecraft.world.item.ItemStack;

public class ScytheComponent extends WarpstoneComponent{
    //MODES
    /*
    0 - concealed - rusty, boring model, attributes mirror iron sword
    no bonus attributes, aside from ability to go invisible

    1 - revealed - standard model, attributes mirror netherite sword, enables lore FX
    warpstone functionality

    2 - war - converts to war-scythe variant, particle FX around player, can be configured for different effects on
    hit, right-clicking allows boosting to a configurable amount

    3 - overdriven - variant of standard with buffs from war, speed buff, and hunger debuff,

     */

    //FUNCTIONS
    /*
    0 - invisibility
    1 - warping
    2 - boosting
    3 - speed, overdriven only, defaults to lower tiers
     */

    //FX
    /*
    0 - poison
    1 - nausea
    2 - slowness
    3 - combustion - overdriven only, defaults to slowness
    4 - wither - overdriven only, defaults to poison
    5 - mining fatigue - overdriven only, defaults to nausea
     */
    public ScytheComponent(ItemStack stack) {
        super(stack);
    }

    public void setMode(int mode){
        this.putInt("Mode",mode);
    }

    public int getMode(){
        try{
            return this.getInt("Mode");
        }catch(Exception e){
            this.setMode(0);
            return this.getInt("Mode");
        }
    }

    public void setFunction(int func){
        if(this.getMode()<1){
            this.putInt("Function",0);
        }else if(this.getMode()<2 && func>1){
            this.putInt("Function",1);
        }else if(this.getMode()<3 && func>2){
            this.putInt("Function",2);
        }else{
            this.putInt("Function",func);
        }
    }
    public int getFunction(){
        try{
            this.setFunction(this.getInt("Function"));
            return this.getInt("Function");
        }catch(Exception e){
            this.putInt("Function",0);
            return this.getInt("Function");
        }
    }

    public void setFX(int fx){
        if(this.getMode()<3 && fx>2){
            if(fx==3){
                this.putInt("FX",2);
            }else if(fx==4){
                this.putInt("FX",0);
            }else if(fx==5){
                this.putInt("FX",1);
            }
        }
            this.putInt("FX",fx);
        }
    public int getFX(){
        try{
            this.setFX(this.getInt("FX"));
            return this.getInt("FX");
        }catch(Exception e){
            this.putInt("FX",0);
            return this.getInt("FX");
        }
    }
}

package io.github.jadedchara.lavender.common.registry;

import io.github.jadedchara.lavender.Lavender;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class LavenderSounds {
    public static final ResourceLocation FEEDBACK_ID = new ResourceLocation("ashfall","feedback");
    public static SoundEvent FEEDBACK_EVENT = new SoundEvent(FEEDBACK_ID);
    public static final ResourceLocation PULSE_ID = new ResourceLocation("ashfall","pulse");
    public static SoundEvent PULSE_EVENT = new SoundEvent(PULSE_ID);

    public static void init(){
        Registry.register(Registry.SOUND_EVENT,FEEDBACK_ID,FEEDBACK_EVENT);
        Registry.register(Registry.SOUND_EVENT,PULSE_ID,PULSE_EVENT);
    }
}

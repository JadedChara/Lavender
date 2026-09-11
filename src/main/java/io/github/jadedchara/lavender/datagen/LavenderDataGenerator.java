package io.github.jadedchara.lavender.datagen;

import io.github.jadedchara.lavender.datagen.providers.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class LavenderDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {

        gen.addProvider(LavenderBlockModelProvider::new);
        gen.addProvider(LavenderLangProvider::new);
        gen.addProvider(LavenderRecipeProvider::new);
        gen.addProvider(LavenderBlockLootProvider::new);
        gen.addProvider(LavenderEntityLootProvider::new);
    }
}

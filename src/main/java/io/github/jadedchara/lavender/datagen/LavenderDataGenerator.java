package io.github.jadedchara.lavender.datagen;

import io.github.jadedchara.lavender.datagen.providers.LavenderBlockModelProvider;
import io.github.jadedchara.lavender.datagen.providers.LavenderLangProvider;
import io.github.jadedchara.lavender.datagen.providers.LavenderRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class LavenderDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {

        gen.addProvider(LavenderBlockModelProvider::new);
        gen.addProvider(LavenderLangProvider::new);
        gen.addProvider(LavenderRecipeProvider::new);
    }
}

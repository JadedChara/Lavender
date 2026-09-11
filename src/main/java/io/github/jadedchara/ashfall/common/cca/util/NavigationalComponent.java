package io.github.jadedchara.ashfall.common.cca.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;


public interface NavigationalComponent{
    void setDimension(ResourceKey<Level> dimension);
    ResourceKey<Level> getDimension();
    void setBoundAnchor(BlockPos pos);
    void removeBoundAnchor();
    BlockPos getBoundAnchor();
}

package io.github.jadedchara.ashfall.common.cca.util;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.core.BlockPos;

import java.util.List;

public interface PositionListComponent extends Component {
    void addPosition(BlockPos bp);
    void removePosition(BlockPos bp);
    BlockPos getPosition(BlockPos bp);
    List<BlockPos> getPositions();
    void clearPositions();
    BlockPos getRandomPosition();
}

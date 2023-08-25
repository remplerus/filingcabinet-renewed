package com.rempler.fcrenewed.util;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.slf4j.Logger;

public class Constants {

    public static final String MODID = "fcrenewed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static class BlockStateProperties {
        public static final BooleanProperty CABINET_SLOT_0_OCCUPIED = BooleanProperty.create("slot_0_occupied");
        public static final BooleanProperty CABINET_SLOT_1_OCCUPIED = BooleanProperty.create("slot_1_occupied");
        public static final BooleanProperty CABINET_SLOT_2_OCCUPIED = BooleanProperty.create("slot_2_occupied");
        public static final BooleanProperty CABINET_SLOT_3_OCCUPIED = BooleanProperty.create("slot_3_occupied");
        public static final BooleanProperty CABINET_SLOT_4_OCCUPIED = BooleanProperty.create("slot_4_occupied");
        public static final BooleanProperty CABINET_SLOT_5_OCCUPIED = BooleanProperty.create("slot_5_occupied");
        public static final BooleanProperty CABINET_SLOT_6_OCCUPIED = BooleanProperty.create("slot_6_occupied");
        public static final BooleanProperty CABINET_SLOT_7_OCCUPIED = BooleanProperty.create("slot_7_occupied");
    }
}

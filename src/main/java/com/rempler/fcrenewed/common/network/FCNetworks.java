package com.rempler.fcrenewed.common.network;

import com.rempler.fcrenewed.util.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class FCNetworks {
    public static SimpleChannel INSTANCE;
    private static int id = 0;
    private static int id() {
        return id++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry
                .newSimpleChannel(new ResourceLocation(Constants.MODID, "network"), () -> "1.0", s -> true, s -> true);

        INSTANCE = net;
    }

    public static <M> void toServer(M message) {
        INSTANCE.sendToServer(message);
    }

    public static <M> void toPlayer(M message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }


}

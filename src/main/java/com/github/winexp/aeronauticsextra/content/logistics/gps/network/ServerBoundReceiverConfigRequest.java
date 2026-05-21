package com.github.winexp.aeronauticsextra.content.logistics.gps.network;

import com.github.winexp.aeronauticsextra.AeronauticsExtra;
import com.github.winexp.aeronauticsextra.content.blocks.geomatics.gps.receiver.GPSReceiverBlockEntity;
import foundry.veil.api.network.handler.PacketContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record ServerBoundReceiverConfigRequest(BlockPos blockPos, Vec3 targetPos, Vec3i maxDistance) implements CustomPacketPayload {
    public static final Type<ServerBoundReceiverConfigRequest> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AeronauticsExtra.MOD_ID, "gps_receiver_config"));

    public static final StreamCodec<ByteBuf, ServerBoundReceiverConfigRequest> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ServerBoundReceiverConfigRequest::blockPos,
            ByteBufCodecs.fromCodec(Vec3.CODEC),
            ServerBoundReceiverConfigRequest::targetPos,
            ByteBufCodecs.fromCodec(Vec3i.CODEC),
            ServerBoundReceiverConfigRequest::maxDistance,
            ServerBoundReceiverConfigRequest::new
    );

    @Override
    public Type<ServerBoundReceiverConfigRequest> type() {
        return TYPE;
    }

    public void handle(PacketContext context) {
        Player player = context.player();
        Level level = player.level();
        BlockPos blockPos = this.blockPos;
        if (!level.isLoaded(blockPos)) return;
        if (level.getBlockEntity(blockPos) instanceof GPSReceiverBlockEntity receiver && receiver.canPlayerUse(player)) {
            receiver.setTargetPos(this.targetPos);
            receiver.setMaxDistance(this.maxDistance);
        }
    }
}

package com.github.winexp.aeronauticsextra.content.logistics.gravity_staff.networking;

import com.github.winexp.aeronauticsextra.AeronauticsExtra;
import com.github.winexp.aeronauticsextra.content.item.GravityStaffItem;
import com.github.winexp.aeronauticsextra.content.logistics.gravity_staff.GravityStaffAction;
import com.github.winexp.aeronauticsextra.mixin_interface.gravity_staff.GravityModifier;
import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import foundry.veil.api.network.handler.PacketContext;
import io.netty.buffer.ByteBuf;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecBuilders;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.UUID;

public record ServerBoundGravityStaffActionRequest(UUID sublevelId, GravityStaffAction action) implements CustomPacketPayload {
    public static final Type<ServerBoundGravityStaffActionRequest> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AeronauticsExtra.MOD_ID, "gravity_staff_action"));

    public static final StreamCodec<ByteBuf, ServerBoundGravityStaffActionRequest> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ServerBoundGravityStaffActionRequest::sublevelId,
            CatnipStreamCodecBuilders.ofEnum(GravityStaffAction.class),
            ServerBoundGravityStaffActionRequest::action,
            ServerBoundGravityStaffActionRequest::new
    );

    @Override
    public Type<ServerBoundGravityStaffActionRequest> type() {
        return TYPE;
    }

    public void handle(PacketContext context) {
        if (!GravityStaffItem.isHolding(context.player())) return;

        if (this.action == GravityStaffAction.TOGGLE_ENABLED) {
            ServerLevel level = (ServerLevel) context.level();
            ServerSubLevelContainer container = SubLevelContainer.getContainer(level);
            ServerSubLevel sublevel = (ServerSubLevel) container.getSubLevel(this.sublevelId);
            if (sublevel == null) return;
            GravityModifier modifier = (GravityModifier) sublevel;
            modifier.aero_extra$setEnabled(!modifier.aero_extra$isEnabled());
        }
    }
}

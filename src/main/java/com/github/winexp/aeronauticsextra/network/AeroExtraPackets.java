package com.github.winexp.aeronauticsextra.network;

import com.github.winexp.aeronauticsextra.AeronauticsExtra;
import com.github.winexp.aeronauticsextra.content.logistics.gps.network.ServerBoundReceiverConfigRequest;
import com.github.winexp.aeronauticsextra.content.logistics.gps.network.ServerBoundSatelliteConfigRequest;
import com.github.winexp.aeronauticsextra.content.logistics.gravity_staff.networking.ServerBoundGravityStaffActionRequest;
import foundry.veil.api.network.VeilPacketManager;

public class AeroExtraPackets {
    private static final VeilPacketManager PACKET_MANAGER = VeilPacketManager.create(AeronauticsExtra.MOD_ID, "1");

    public static void init() {
        PACKET_MANAGER.registerServerbound(ServerBoundSatelliteConfigRequest.TYPE, ServerBoundSatelliteConfigRequest.STREAM_CODEC, ServerBoundSatelliteConfigRequest::handle);
        PACKET_MANAGER.registerServerbound(ServerBoundReceiverConfigRequest.TYPE, ServerBoundReceiverConfigRequest.STREAM_CODEC, ServerBoundReceiverConfigRequest::handle);
        PACKET_MANAGER.registerServerbound(ServerBoundGravityStaffActionRequest.TYPE, ServerBoundGravityStaffActionRequest.STREAM_CODEC, ServerBoundGravityStaffActionRequest::handle);
    }
}

package com.github.winexp.aeronauticsextra.compat.computercraft.peripherals.aero_extra;

import com.github.winexp.aeronauticsextra.content.blocks.geomatics.gps.receiver.GPSReceiverBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;
import dev.simulated_team.simulated.compat.computercraft.peripherals.SimPeripheral;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GPSReceiverPeripheral extends SimPeripheral<GPSReceiverBlockEntity> {
    public GPSReceiverPeripheral(GPSReceiverBlockEntity blockEntity) {
        super(blockEntity);
    }

    @LuaFunction
    public final List<Double> getCurrentPos() {
        Vec3 position = this.blockEntity.getCurrentPos();
        if (position == null) return null;
        else return List.of(position.x, position.y, position.z);
    }

    @LuaFunction
    public final List<Double> getTargetPos() {
        Vec3 position = this.blockEntity.getTargetPos();
        return List.of(position.x, position.y, position.z);
    }

    @LuaFunction
    public final void setTargetPos(double x, double y, double z) {
        this.blockEntity.setTargetPos(new Vec3(x, y, z));
    }

    @LuaFunction
    public final List<Integer> getMaxDistance() {
        Vec3i maxDistance = this.blockEntity.getMaxDistance();
        return List.of(maxDistance.getX(), maxDistance.getY(), maxDistance.getZ());
    }

    @LuaFunction
    public final void setMaxDistance(int x, int y, int z) {
        this.blockEntity.setMaxDistance(new Vec3i(x, y, z));
    }

    @LuaFunction
    public final boolean isUpdated() {
        return this.blockEntity.isUpdated();
    }

    @Override
    public String getType() {
        return "aero_extra:gps_receiver";
    }
}

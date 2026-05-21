package com.github.winexp.aeronauticsextra.compat.computercraft.peripherals.aeronautics;

import com.github.winexp.aeronauticsextra.mixin_interface.accessor.HotAirBurnerAccessor;
import dan200.computercraft.api.lua.LuaFunction;
import dev.eriksonn.aeronautics.content.blocks.hot_air.steam_vent.SteamVentBlockEntity;
import dev.simulated_team.simulated.compat.computercraft.peripherals.SimPeripheral;

public class SteamVentPeripheral extends SimPeripheral<SteamVentBlockEntity> {
    public SteamVentPeripheral(SteamVentBlockEntity blockEntity) {
        super(blockEntity);
    }

    @LuaFunction
    public final int getSteamAmount() {
        return ((HotAirBurnerAccessor) this.blockEntity).aero_extra$getHotAirAmount();
    }

    @LuaFunction
    public final void setSteamAmount(int amount) {
        ((HotAirBurnerAccessor) this.blockEntity).aero_extra$setHotAirAmount(amount);
    }

    @Override
    public String getType() {
        return "steam_vent";
    }
}

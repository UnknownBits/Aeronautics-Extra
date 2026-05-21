package com.github.winexp.aeronauticsextra.compat.computercraft;

import com.github.winexp.aeronauticsextra.AeronauticsExtra;
import com.github.winexp.aeronauticsextra.compat.computercraft.peripherals.aero_extra.CVTGearshiftPeripheral;
import com.github.winexp.aeronauticsextra.compat.computercraft.peripherals.aero_extra.GPSReceiverPeripheral;
import com.github.winexp.aeronauticsextra.compat.computercraft.peripherals.aeronautics.HotAirBurnerPeripheral;
import com.github.winexp.aeronauticsextra.compat.computercraft.peripherals.aeronautics.SteamVentPeripheral;
import com.github.winexp.aeronauticsextra.compat.computercraft.peripherals.offroad.WheelMountPeripheral;
import com.github.winexp.aeronauticsextra.registry.AeroExtraBlockEntityTypes;
import dev.eriksonn.aeronautics.index.AeroBlockEntityTypes;
import dev.ryanhcode.offroad.index.OffroadBlockEntityTypes;
import dev.simulated_team.simulated.service.ServiceUtil;
import dev.simulated_team.simulated.service.SimModCompatibilityService;
import dev.simulated_team.simulated.service.compat.SimPeripheralService;

public class ComputerCraftPeripherals implements SimModCompatibilityService {
    @Override
    public void init() {
        SimPeripheralService service = ServiceUtil.load(SimPeripheralService.class);
        AeronauticsExtra.LOGGER.info("Loading Simulated peripherals...");
        service.addPeripheral(AeroBlockEntityTypes.HOT_AIR_BURNER, HotAirBurnerPeripheral::new);
        service.addPeripheral(AeroBlockEntityTypes.STEAM_VENT, SteamVentPeripheral::new);
        service.addPeripheral(OffroadBlockEntityTypes.WHEEL_MOUNT, WheelMountPeripheral::new);

        AeronauticsExtra.LOGGER.info("Loading Aeronautics Extra peripherals...");
        service.addPeripheral(AeroExtraBlockEntityTypes.GPS_RECEIVER, GPSReceiverPeripheral::new);
        service.addPeripheral(AeroExtraBlockEntityTypes.CVT_GEARSHIFT, CVTGearshiftPeripheral::new);
    }

    @Override
    public String getModId() {
        return "computercraft";
    }
}

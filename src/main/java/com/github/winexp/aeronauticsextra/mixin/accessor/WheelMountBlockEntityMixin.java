package com.github.winexp.aeronauticsextra.mixin.accessor;

import com.github.winexp.aeronauticsextra.mixin_interface.accessor.WheelMountAccessor;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import dev.ryanhcode.offroad.content.blocks.wheel_mount.WheelMountBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WheelMountBlockEntity.class)
public class WheelMountBlockEntityMixin implements WheelMountAccessor {
    @Shadow
    private ScrollValueBehaviour strength;

    @Override
    public int aero_extra$getStrength() {
        return this.strength.getValue();
    }

    @Override
    public void aero_extra$setStrength(int strength) {
        this.strength.setValue(strength);
    }
}

package com.github.winexp.aeronauticsextra.mixin.gravity_staff;

import com.github.winexp.aeronauticsextra.content.item.GravityStaffItem;
import com.github.winexp.aeronauticsextra.mixin_interface.gravity_staff.GravityModifier;
import dev.ryanhcode.sable.api.physics.force.ForceTotal;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.system.SubLevelPhysicsSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerSubLevel.class)
public abstract class ServerSubLevelMixin implements GravityModifier {
    @Unique
    private boolean aero_extra$no_gravity = false;

    @Unique
    private final ForceTotal aero_extra$forceTotal = new ForceTotal();

    @Inject(method = "prePhysicsTick", at = @At("HEAD"))
    private void handleNoGravity(SubLevelPhysicsSystem physicsSystem, RigidBodyHandle handle, double timeStep, CallbackInfo ci) {
        ServerSubLevel sublevel = (ServerSubLevel) (Object) this;
        if (this.aero_extra$no_gravity) {
            GravityStaffItem.handleNoGravity(sublevel, physicsSystem, handle, timeStep, this.aero_extra$forceTotal);
        }
        handle.applyForcesAndReset(this.aero_extra$forceTotal);
    }

    @Override
    public boolean aero_extra$isEnabled() {
        return this.aero_extra$no_gravity;
    }

    @Override
    public void aero_extra$setEnabled(boolean enabled) {
        this.aero_extra$no_gravity = enabled;
    }
}

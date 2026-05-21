package com.github.winexp.aeronauticsextra.mixin.accessor;

import com.github.winexp.aeronauticsextra.mixin_interface.accessor.WheelMountAccessor;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import dev.ryanhcode.offroad.content.blocks.wheel_mount.WheelMountBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Mixin(WheelMountBlockEntity.class)
public abstract class WheelMountBlockEntityMixin implements WheelMountAccessor {
    @Unique
    private Field aero_extra$strengthField;
    @Unique
    private Method aero_extra$getValueMethod;
    @Unique
    private Method aero_extra$setValueMethod;

    @Unique
    private void aero_extra$loadFieldsAndMethods() {
        if (this.aero_extra$strengthField != null) return;
        try {
            Class<?> outer = WheelMountBlockEntity.class;
            Field field = outer.getDeclaredField("strength");
            field.setAccessible(true);
            this.aero_extra$strengthField = field;

            Class<?> clazz = ScrollValueBehaviour.class;
            Method getValue = clazz.getDeclaredMethod("getValue");
            getValue.setAccessible(true);
            this.aero_extra$getValueMethod = getValue;

            Method setValue = clazz.getDeclaredMethod("setValue", int.class);
            setValue.setAccessible(true);
            this.aero_extra$setValueMethod = setValue;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public int aero_extra$getStrength() {
        this.aero_extra$loadFieldsAndMethods();
        try {
            return (int) this.aero_extra$getValueMethod.invoke(this.aero_extra$strengthField.get(this));
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public void aero_extra$setStrength(int strength) {
        this.aero_extra$loadFieldsAndMethods();
        try {
            this.aero_extra$setValueMethod.invoke(this.aero_extra$strengthField.get(this), strength);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}

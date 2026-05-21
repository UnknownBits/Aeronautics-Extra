package com.github.winexp.aeronauticsextra.content.item;

import com.github.winexp.aeronauticsextra.content.logistics.gravity_staff.GravityStaffAction;
import com.github.winexp.aeronauticsextra.content.logistics.gravity_staff.networking.ServerBoundGravityStaffActionRequest;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.physics.force.ForceTotal;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.physics.config.dimension_physics.DimensionPhysicsData;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import dev.ryanhcode.sable.sublevel.system.SubLevelPhysicsSystem;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.joml.Vector3d;

public class GravityStaffItem extends Item {
    public static float RANGE = 128.0f;

    public GravityStaffItem(Properties properties) {
        super(properties);
    }

    public static boolean isHolding(Player player) {
        return player.getMainHandItem().getItem() instanceof GravityStaffItem ||
                player.getOffhandItem().getItem() instanceof GravityStaffItem;
    }

    public static void handleNoGravity(ServerSubLevel sublevel, SubLevelPhysicsSystem physicsSystem, RigidBodyHandle handle, double timeStep, ForceTotal output) {
        Vector3d gravity = DimensionPhysicsData.getGravity(sublevel.getLevel());
        double mass = sublevel.getMassTracker().getMass();
        Vector3d force = sublevel.logicalPose().transformNormalInverse(gravity.negate().mul(mass).mul(timeStep).mul(0.9985));
        output.applyLinearImpulse(force);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!level.isClientSide) return InteractionResultHolder.fail(stack);
        HitResult hitResult = player.pick(RANGE, 1, false);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.fail(stack);
        }
        SubLevel sublevel = Sable.HELPER.getContainingClient(hitResult.getLocation());
        if (sublevel != null) {
            CatnipServices.NETWORK.sendToServer(new ServerBoundGravityStaffActionRequest(sublevel.getUniqueId(), GravityStaffAction.TOGGLE_ENABLED));
            return InteractionResultHolder.success(stack);
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player player) {
        return false;
    }
}

package net.test.NoNameNoNa.Bar;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

//从Raid（袭击）里借过来的

public class LightingBar{
    private static final Component RAID_NAME_COMPONENT = Component.translatable("lightinglevel");
    private final ServerBossEvent raidEvent = new ServerBossEvent(RAID_NAME_COMPONENT, BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
    private BlockPos center;
    private final ServerLevel level;
    private boolean isBarVisible = false;

    public LightingBar(ServerLevel level, BlockPos center){
        this.level=level;
        this.raidEvent.setProgress(0.0F);
        this.center=center;
    }

    private Predicate<ServerPlayer> validPlayer() {
        return vars -> {
            BlockPos blockpos = vars.blockPosition();
            return vars.isAlive();
        };
    }

    public void addPlayers() {
        Set<ServerPlayer> set = Sets.newHashSet(this.raidEvent.getPlayers());
        List<ServerPlayer> list = this.level.getPlayers(this.validPlayer());

        for (ServerPlayer serverplayer : list) {
            if (!set.contains(serverplayer)) {
                this.raidEvent.addPlayer(serverplayer);
            }
        }
    }

    public void removePlayers() {
        Set<ServerPlayer> set = Sets.newHashSet(this.raidEvent.getPlayers());
        List<ServerPlayer> list = this.level.getPlayers(this.validPlayer());

        for (ServerPlayer serverplayer : set) {
            this.raidEvent.removePlayer(serverplayer);
        }
    }

    public void updateBossbar(int var) {
        this.raidEvent.setProgress((float)var/15F);
    }

    public void toggleVisibility() {
        if (isBarVisible) {
            removePlayers();
        } else {
            addPlayers();
        }
        isBarVisible = !isBarVisible;
    }
}
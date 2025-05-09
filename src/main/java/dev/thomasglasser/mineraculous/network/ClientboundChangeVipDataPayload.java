package dev.thomasglasser.mineraculous.network;

import dev.thomasglasser.mineraculous.Mineraculous;
import dev.thomasglasser.mineraculous.client.MineraculousClientUtils;
import dev.thomasglasser.mineraculous.client.renderer.entity.layers.VipData;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public record ClientboundChangeVipDataPayload(UUID uuid, VipData vipData) implements ExtendedPacketPayload {
    public static final Type<ClientboundChangeVipDataPayload> TYPE = new Type<>(Mineraculous.modLoc("clientbound_change_vip_data"));
    public static final StreamCodec<FriendlyByteBuf, ClientboundChangeVipDataPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundChangeVipDataPayload::uuid,
            VipData.STREAM_CODEC, ClientboundChangeVipDataPayload::vipData,
            ClientboundChangeVipDataPayload::new);

    // ON CLIENT
    @Override
    public void handle(@Nullable Player player) {
        MineraculousClientUtils.setVipData(ClientUtils.getPlayerByUUID(uuid), vipData);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

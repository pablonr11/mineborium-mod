package com.mundobachata.mineborium.networking.packet;

import com.mundobachata.mineborium.client.ClientAbstinenceData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AbstinenceDataSyncS2CPacket {
    private final int abstinence;
    private final boolean hasSmoked;

    public AbstinenceDataSyncS2CPacket(int abstinence, boolean hasSmoked) {
        this.abstinence = abstinence;
        this.hasSmoked = hasSmoked;
    }

    public AbstinenceDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.abstinence = buf.readInt();
        this.hasSmoked = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(abstinence);
        buf.writeBoolean(hasSmoked);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client side
            ClientAbstinenceData.setPlayerAbstinence(abstinence);
            ClientAbstinenceData.setHasSmoked(hasSmoked);
        });

        return true;
    }

}

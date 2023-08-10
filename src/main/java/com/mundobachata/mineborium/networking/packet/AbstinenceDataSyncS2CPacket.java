package com.mundobachata.mineborium.networking.packet;

import com.mundobachata.mineborium.client.ClientAbstinenceData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AbstinenceDataSyncS2CPacket {
    private final int abstinence;

    public AbstinenceDataSyncS2CPacket(int abstinence) {
        this.abstinence = abstinence;
    }

    public AbstinenceDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.abstinence = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(abstinence);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client side
            ClientAbstinenceData.set(abstinence);
        });

        return true;
    }

}

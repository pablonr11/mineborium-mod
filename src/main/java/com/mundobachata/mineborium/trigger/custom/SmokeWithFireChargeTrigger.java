package com.mundobachata.mineborium.trigger.custom;

import com.google.gson.JsonObject;
import com.mundobachata.mineborium.Mineborium;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SmokeWithFireChargeTrigger extends SimpleCriterionTrigger<SmokeWithFireChargeTrigger.TriggerInstance> {

    private final static ResourceLocation ID = new ResourceLocation(Mineborium.MOD_ID,
            "smoke_with_fire_charge");

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate contextAwarePredicate,
                                             DeserializationContext deserializationContext) {
        return new SmokeWithFireChargeTrigger.TriggerInstance(ID, contextAwarePredicate);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, (triggerInstance) -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation resourceLocation, ContextAwarePredicate contextAwarePredicate) {
            super(resourceLocation, contextAwarePredicate);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext serializationContext) {
            return super.serializeToJson(serializationContext);
        }
    }

}

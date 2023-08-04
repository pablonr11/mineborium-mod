package com.mundobachata.mineborium.trigger.custom;

import com.google.gson.JsonObject;
import com.mundobachata.mineborium.Mineborium;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class DeathByCigaretteTrigger extends SimpleCriterionTrigger<DeathByCigaretteTrigger.TiggerInstace> {

    private final static ResourceLocation ID = new ResourceLocation(Mineborium.MOD_ID,
            "death_by_cigarette");

    @Override
    protected TiggerInstace createInstance(JsonObject json, ContextAwarePredicate contextAwarePredicate,
                                           DeserializationContext deserializationContext) {
        return new DeathByCigaretteTrigger.TiggerInstace(ID, contextAwarePredicate);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, (triggerInstance) -> true);
    }

    public static class TiggerInstace extends AbstractCriterionTriggerInstance {

        public TiggerInstace(ResourceLocation resourceLocation, ContextAwarePredicate contextAwarePredicate) {
            super(resourceLocation, contextAwarePredicate);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext serializationContext) {
            return super.serializeToJson(serializationContext);
        }
    }

}

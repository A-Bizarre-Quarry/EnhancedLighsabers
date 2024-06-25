package de.abq.enhanced_lightsabers.common.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;


import java.util.function.Consumer;

public record PartsComponents(int value1, boolean value2) implements TooltipProvider {
    public static final Codec<PartsComponents> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("value1").forGetter(PartsComponents::value1),
                    Codec.BOOL.fieldOf("value2").forGetter(PartsComponents::value2)
            ).apply(instance, PartsComponents::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PartsComponents> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, PartsComponents::value1,
            ByteBufCodecs.BOOL, PartsComponents::value2,
            PartsComponents::new
    );
    // Unit stream codec if nothing should be sent across the network
    public static final StreamCodec<RegistryFriendlyByteBuf, PartsComponents> UNIT_STREAM_CODEC = StreamCodec.unit(new PartsComponents(0, false));
    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {

    }
}

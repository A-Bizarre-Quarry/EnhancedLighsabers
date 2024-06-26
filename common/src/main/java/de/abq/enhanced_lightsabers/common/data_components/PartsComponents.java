package de.abq.enhanced_lightsabers.common.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.abq.enhanced_lightsabers.common.data_components.parts.BladesPart;
import de.abq.enhanced_lightsabers.common.data_components.parts.GripPart;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;


import java.util.function.Consumer;

public record PartsComponents(GripPart grip, BladesPart blades) implements TooltipProvider {
    public static final PartsComponents DEFAULT = new PartsComponents(
            GripPart.DEFAULT,
            BladesPart.DEFAULT
    );

    public static final Codec<PartsComponents> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    GripPart.CODEC.optionalFieldOf("grip", GripPart.DEFAULT).forGetter(PartsComponents::grip),
                    BladesPart.CODEC.optionalFieldOf("blades", BladesPart.DEFAULT).forGetter(PartsComponents::blades)
            ).apply(instance, PartsComponents::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PartsComponents> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(GripPart.CODEC), PartsComponents::grip,
            ByteBufCodecs.fromCodec(BladesPart.CODEC), PartsComponents::blades,
            PartsComponents::new
    );
    // Unit stream codec if nothing should be sent across the network
    public static final StreamCodec<RegistryFriendlyByteBuf, PartsComponents> UNIT_STREAM_CODEC = StreamCodec.unit( DEFAULT );

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
    }
}

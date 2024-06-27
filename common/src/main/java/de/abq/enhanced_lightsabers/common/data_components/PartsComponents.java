package de.abq.enhanced_lightsabers.common.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.abq.enhanced_lightsabers.common.data_components.parts.BladesPart;
import de.abq.enhanced_lightsabers.common.data_components.parts.ModelPart;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.enchantment.Enchantment;


import java.util.Iterator;
import java.util.function.Consumer;

public record PartsComponents(ModelPart emitter, ModelPart guard, ModelPart grip, ModelPart pommel, BladesPart blades) implements TooltipProvider {
    public static final PartsComponents DEFAULT = new PartsComponents(
            ModelPart.DEFAULT,
            ModelPart.DEFAULT,
            ModelPart.DEFAULT,
            ModelPart.DEFAULT,
            BladesPart.DEFAULT
    );

    public static final Codec<PartsComponents> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ModelPart.CODEC.optionalFieldOf("emitter", ModelPart.DEFAULT).forGetter(PartsComponents::emitter),
                    ModelPart.CODEC.optionalFieldOf("guard", ModelPart.DEFAULT).forGetter(PartsComponents::guard),
                    ModelPart.CODEC.optionalFieldOf("grip", ModelPart.DEFAULT).forGetter(PartsComponents::grip),
                    ModelPart.CODEC.optionalFieldOf("pommel", ModelPart.DEFAULT).forGetter(PartsComponents::pommel),
                    BladesPart.CODEC.optionalFieldOf("blades", BladesPart.DEFAULT).forGetter(PartsComponents::blades)
            ).apply(instance, PartsComponents::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PartsComponents> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(ModelPart.CODEC), PartsComponents::emitter,
            ByteBufCodecs.fromCodec(ModelPart.CODEC), PartsComponents::guard,
            ByteBufCodecs.fromCodec(ModelPart.CODEC), PartsComponents::grip,
            ByteBufCodecs.fromCodec(ModelPart.CODEC), PartsComponents::pommel,
            ByteBufCodecs.fromCodec(BladesPart.CODEC), PartsComponents::blades,
            PartsComponents::new
    );
    // Unit stream codec if nothing should be sent across the network
    public static final StreamCodec<RegistryFriendlyByteBuf, PartsComponents> UNIT_STREAM_CODEC = StreamCodec.unit( DEFAULT );

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {}
}

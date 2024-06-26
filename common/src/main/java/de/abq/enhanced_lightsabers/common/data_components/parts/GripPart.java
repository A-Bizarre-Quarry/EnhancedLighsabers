package de.abq.enhanced_lightsabers.common.data_components.parts;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.abq.enhanced_lightsabers.util.Util;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record GripPart(ResourceLocation model, float scale){
    public static final GripPart DEFAULT = new GripPart(Util.EMPTY_RESOURCE_LOCATION, 0);

    public static Codec<GripPart> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("model").forGetter(GripPart::model),
                    Codec.FLOAT.fieldOf("scale").forGetter(GripPart::scale)
            ).apply(instance, GripPart::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, GripPart> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(ResourceLocation.CODEC), GripPart::model,
            ByteBufCodecs.FLOAT, GripPart::scale,
            GripPart::new
    );
    // Unit stream codec if nothing should be sent across the network
    public static final StreamCodec<RegistryFriendlyByteBuf, GripPart> UNIT_STREAM_CODEC = StreamCodec.unit(DEFAULT);
}

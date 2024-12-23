package de.abq.partium.addon_system;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.*;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.flag.FeatureFlagSet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomPackFinder implements RepositorySource {
    private final File addonsDirectory;

    public CustomPackFinder(File addonsDirectory) {
        this.addonsDirectory = addonsDirectory;
    }

    @Override
    public void loadPacks(Consumer<Pack> infoConsumer) {
      /*
        if (addonsDirectory.exists() && addonsDirectory.isDirectory()) {
            for (File addonFolder : addonsDirectory.listFiles()) {
                if (addonFolder.isDirectory()) {
                    String packName = addonFolder.getName();
                    CustomPackResources packResources = new CustomPackResources(
                            packName,
                            addonFolder.toPath(),
                            PackType.CLIENT_RESOURCES // or PackType.SERVER_DATA based on your needs
                    );
// PackLocationInfo pLocation, ResourcesSupplier pResources, Metadata pMetadata, PackSelectionConfig pSelectionConfig
                    Pack pack = Pack(
//String id, Component title, PackSource source, Optional< KnownPack > knownPackInfo
                            new PackLocationInfo(addonFolder.getName(), Component.literal("Tst"), PackSource.DEFAULT, Optional.empty() ),
                            () -> packResources,
//Component description, PackCompatibility compatibility, FeatureFlagSet requestedFeatures, List<String> overlays, boolean isHidden
                            new Pack.Metadata()

                    );

                    infoConsumer.accept(pack);
                }
            }
        }*/
    }
}

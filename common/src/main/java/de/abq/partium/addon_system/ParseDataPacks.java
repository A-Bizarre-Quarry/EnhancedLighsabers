package de.abq.partium.addon_system;

import com.google.gson.*;
import de.abq.partium.Partium;
import de.abq.partium.addon_system.json_objects.PartObject;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.OverlayMetadataSection;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.IoSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

//NOTE: Make this managed by recipes?

public class ParseDataPacks {
    private HashSet<PartObject> serverParts;

    public static void scatch(){
        Minecraft.getInstance().getResourceManager().listPacks().forEach(packResources -> {
            System.out.println(packResources.location()+": " +packResources.knownPackInfo());
        });
    }

    public void printDatapacks(MinecraftServer server) {
        HashSet<PartObject> availableParts = new HashSet<>();
        HashSet<String> enabledParts = new HashSet<>();
        server.getResourceManager().listPacks().forEach(packResources -> {

            for (String namespace : packResources.getNamespaces(PackType.SERVER_DATA)){
                if (namespace.equals(Partium.MOD_ID)){
                    IoSupplier<InputStream> supplier = packResources.getResource(PackType.SERVER_DATA, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "disabled.json"));
                    enabledParts.addAll(getEnabledParts(supplier));
                }

                Map<ResourceLocation, IoSupplier<InputStream>> resources = new HashMap<>();
                packResources.listResources(PackType.SERVER_DATA, namespace, "parts", resources::put);
                resources.forEach((rl, stream) -> {
                            if (rl.getPath().endsWith(".json")) return;
                            getAvailableParts(stream).ifPresent(availableParts::add);
                        }
                );
            }
        });
    }

    private Optional<PartObject> getAvailableParts(IoSupplier<InputStream> ioSupplier){
        try {
            InputStream inputStream = ioSupplier.get();
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonElement idObject = jsonObject.get("id");
            JsonElement typeObject = jsonObject.get("type");

            return Optional.of(new PartObject(idObject.getAsString(), typeObject.getAsString()));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Set<String> getEnabledParts(IoSupplier<InputStream> ioSupplier){
        Set<String> ret = new HashSet<>();
        if (ioSupplier == null){ return ret; }
        try {
            //TODO: just use a resource pack and find out if the server can read resource packs, if not just put the rp somewhere and parse it my self.
            //      would be cool if i could add a partium_addon to the resource pack location. I could parse the rest myself
            InputStream inputStream = ioSupplier.get();
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonArray partsObject = jsonObject.getAsJsonArray("parts");
            partsObject.forEach(partId -> {
                System.out.println(partId);
                ret.add(partId.getAsString());
            });

        } catch (IOException e) {
            Partium.LOG.error(e.getMessage());
        }
        return ret;
    }
}
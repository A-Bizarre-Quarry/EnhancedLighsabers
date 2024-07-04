package de.abq.partium.addon_system;

import com.google.gson.*;
import de.abq.partium.Partium;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackType;

import java.io.IOException;
import java.io.InputStream;

public class ParseDataPacks {

    public static void scatch(){
        Minecraft.getInstance().getResourceManager().listPacks().forEach(packResources -> {
            System.out.println(packResources.location()+": " +packResources.knownPackInfo());
        });
    }

    public static void printDatapacks(MinecraftServer server) {
        server.getResourceManager().listPacks().forEach(packResources -> {

            System.out.println(packResources.getNamespaces(PackType.CLIENT_RESOURCES));
            if (packResources.getNamespaces(PackType.CLIENT_RESOURCES).contains(Partium.MOD_ID)){
                try {
                    //TODO: just use a resource pack and find out if the server can read resource packs, if not just put the rp somewhere and parse it my self.
                    //      would be cool if i could add a partium_addon to the resource pack location. I could parse the rest myself
                    InputStream inputStream = packResources.getResource(PackType.CLIENT_RESOURCES, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "parts")).get();
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();
                    String json = new String(buffer, "UTF-8");
                    JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                    JsonElement packsObject = jsonObject.get("packs");
                    System.out.println(packsObject);
                } catch (IOException e) {
                    Partium.LOG.error(e.getMessage());
                }
            }
        });
    }
}
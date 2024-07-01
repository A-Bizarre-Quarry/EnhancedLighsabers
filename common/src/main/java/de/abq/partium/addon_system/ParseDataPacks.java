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
            if (packResources.getNamespaces(PackType.SERVER_DATA).contains(Partium.MOD_ID)){
                try {
                    InputStream inputStream = packResources.getResource(PackType.SERVER_DATA, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "parts")).get();
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();
                    String json = new String(buffer, "UTF-8");
                    JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                    JsonElement packsObject = jsonObject.get("packs");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
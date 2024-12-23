package de.abq.partium.client.renderer;

import com.mojang.blaze3d.vertex.VertexFormat;
import de.abq.partium.Partium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ShaderLoader {
    private static ShaderInstance shaderInstance;

    public static void loadShaders() {
        try {
            ResourceLocation vertexShaderLocation = ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "shaders/core/lightsaber.vsh");
            ResourceLocation fragmentShaderLocation = ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "shaders/core/lightsaber.fsh");

            String vertexShaderCode = loadShaderCode(vertexShaderLocation);
            String fragmentShaderCode = loadShaderCode(fragmentShaderLocation);

            int vertexShader = createShader(GL20.GL_VERTEX_SHADER, vertexShaderCode);
            int fragmentShader = createShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderCode);

            shaderInstance = new ShaderInstance(Minecraft.getInstance().getResourceManager(), ResourceLocation.fromNamespaceAndPath(
             Partium.MOD_ID,"lightsaber").toString(),VertexFormat.builder().build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String loadShaderCode(ResourceLocation location) throws IOException {
        System.out.println(Minecraft.getInstance().getResourceManager().getResource(location).toString());
        Path path = Paths.get(Minecraft.getInstance().getResourceManager().getResource(location).toString());
        return new String(Files.readAllBytes(path));
    }

    private static int createShader(int type, String code) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, code);
        GL20.glCompileShader(shader);

        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            System.err.println("Shader compilation failed: " + GL20.glGetShaderInfoLog(shader));
        }

        return shader;
    }

    public static ShaderInstance getShaderInstance() {
        return shaderInstance;
    }
}

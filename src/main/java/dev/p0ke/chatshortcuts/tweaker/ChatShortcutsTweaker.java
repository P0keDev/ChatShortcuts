package dev.p0ke.chatshortcuts.tweaker;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.List;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.SortingIndex(1)
public class ChatShortcutsTweaker implements ITweaker {

	@Override
	public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) { }

	@Override
	public void injectIntoClassLoader(LaunchClassLoader classLoader) {
		MixinBootstrap.init();
		try {
			Class<?> mixinsClass = Class.forName("org.spongepowered.asm.mixin.Mixins");
            Method addConfigurationMethod = mixinsClass.getMethod("addConfiguration", String.class);
            addConfigurationMethod.invoke(null, "mixins.chatshortcuts.json");
		} catch (Exception e) {
			System.out.println("Unable to add mixins configuration. Using liteloader?");
		}
		
		MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
		MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
		
		CodeSource codeSource = getClass().getProtectionDomain().getCodeSource();
        if (codeSource != null) {
            URL location = codeSource.getLocation();
            try {
                File file = new File(location.toURI());
                if (file.isFile()) {
                    CoreModManager.getIgnoredMods().remove(file.getName());
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No CodeSource, if this is not a development environment we might run into problems!");
        }
	}

	@Override
	public String getLaunchTarget() {
		return "net.minecraft.client.main.Main";
	}

	@Override
	public String[] getLaunchArguments() {
		return new String[0];
	}

}

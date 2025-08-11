package top.figsq.poketip.poketip.common;

import lombok.val;
import org.bukkit.plugin.java.JavaPlugin;
import top.figsq.poketip.poketip.api.IListener;
import top.figsq.poketip.poketip.api.PokeTipAPI;

/**
 * 继承该类命名方式
 * {@code top.figsq.poketip.poketip.PokeTip}
 */
public abstract class PokeTipPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        setCommand();
        for (IListener listener : getListeners()) listener.register();
        reloadConfig();
    }

    public void setCommand() {
        val command = getCommand("poketip");
        assert command != null;
        command.setExecutor(CommandBase.INSTANCE);
        command.setTabCompleter(CommandBase.INSTANCE);
    }

    @Override
    public void reloadConfig() {
        this.saveDefaultConfig();
        super.reloadConfig();

        /*==>ILoadable<==*/
        TipConfigManager.INSTANCE.load();
    }

    /**
     * 重写该方法可以方便注册通用监听器
     */
    public IListener[] getListeners() {
        return new IListener[0];
    }

    /**
     * 获取PokeTip插件实例
     */
    public static PokeTipPlugin getInstance() {
        return JavaPlugin.getPlugin(PokeTipPlugin.class);
    }

    /**
     * 获取被实现的api
     */
    public static PokeTipAPI getPokeAPI() {
        return PokeTipPlugin.getInstance().getAPI();
    }


    /*==>以下是每个版本不同需要被实现的<==*/
    public abstract PokeTipAPI getAPI();
}

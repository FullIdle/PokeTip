package top.figsq.poketip.poketip.common;

import lombok.val;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import top.figsq.poketip.poketip.api.ILoadable;
import top.figsq.poketip.poketip.api.SpeciesWrapperPool;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;
import top.figsq.poketip.poketip.common.speciespool.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TipConfigManager implements ILoadable {
    public static final TipConfigManager INSTANCE = new TipConfigManager();
    public File folder = new File(PokeTipPlugin.getInstance().getDataFolder(), "tip-configs");
    public Collection<TipConfigPool> loadTipConfig;

    @Override
    public void load() {
        if (!folder.exists()) PokeTipPlugin.getInstance().saveResource("tip-configs/legend.yml", false);
        val files = folder.listFiles();
        loadTipConfig = new ArrayList<>();
        if (files != null) for (File file : files) loadTipConfig.add(parse(file));
    }

    /**
     * 检查目标物种包含的所有配置的所有提示池
     */
    public static List<TipConfigPool> check(ISpeciesWrapper<?> speciesWrapper) {
        val list = new ArrayList<TipConfigPool>();
        for (TipConfigPool pool : INSTANCE.loadTipConfig) if (pool.contains(speciesWrapper)) list.add(pool);
        return list;
    }

    /**
     * 同上，不过只返回第一个，速度更快
     */
    public static TipConfigPool checkFirst(ISpeciesWrapper<?> wrapper) {
        for (TipConfigPool pool : INSTANCE.loadTipConfig) if (pool.contains(wrapper)) return pool;
        return null;
    }

    public static TipConfigPool parse(File file) {
        if (!file.exists()) throw new IllegalArgumentException("File not exists");
        if (file.isDirectory()) throw new IllegalArgumentException("File is directory");
        val yaml = YamlConfiguration.loadConfiguration(file);
        return new TipConfigPool(
                parsePool(yaml.getStringList("includes")),
                parsePool(yaml.getStringList("excludes")),
                yaml.getString("tips.spawn"),
                yaml.getString("tips.capture")
        );
    }

    public static SpeciesWrapperPool parsePool(List<String> poolsStr) {
        if (poolsStr.isEmpty()) return EmptyPool.INSTANCE;
        val logger = PokeTipPlugin.getInstance().getLogger();
        if (poolsStr.size() == 1) {
            SpeciesWrapperPool pool = parsePool(poolsStr.get(0));
            if (pool == null) logger.warning("Can't parse pool: " + poolsStr.get(0));
            return pool;
        }

        val list = new ArrayList<SpeciesWrapperPool>();
        for (String s : poolsStr) {
            val pool = parsePool(s);
            if (pool == null) {
                logger.warning("Can't parse pool: " + s);
                continue;
            }
            list.add(pool);
        }
        return new CompositePool(list);
    }

    public static SpeciesWrapperPool parsePool(String poolStr) {
        val lowerCase = poolStr.toLowerCase();
        switch (lowerCase) {
            case "{all}":
                return AllPool.INSTANCE;
            case "{legend}":
                return LegendPool.INSTANCE;
            case "{mythical}":
                return MythicalPool.INSTANCE;
        }
        //特殊的
        //世代判断
        if (lowerCase.startsWith("{gen(")) {
            val index = lowerCase.indexOf(")");
            if (index == -1) return null;
            try {
                val gen = Integer.parseInt(lowerCase.substring(4, index));
                return new GenerationPool(gen);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        /*数字编号/物种名*/
        val speciesFactory = PokeTipPlugin.getPokeAPI().getSpeciesWrapperFactory();
        try {
            ISpeciesWrapper<?> species = NumberUtils.isDigits(lowerCase) ? speciesFactory.create(Integer.parseInt(lowerCase)) : speciesFactory.create(lowerCase);
            return new SimplePool(species);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

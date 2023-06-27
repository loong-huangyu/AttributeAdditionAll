package cn.mistymc.www.mtmc.attributeadditionall;

import cn.mistymc.www.mtmc.attributeadditionall.attribute.Armor;
import cn.mistymc.www.mtmc.attributeadditionall.attribute.SpeedBonus;
import cn.mistymc.www.mtmc.attributeadditionall.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class AttributeAdditionAll extends JavaPlugin {
    private static AttributeAdditionAll instance;

    public void onLoad() {
        instance = this;
        (new Armor()).registerAttribute();
        (new SpeedBonus()).registerAttribute();
    }

    public void onEnable() {
        PlayerListener playerListener = new PlayerListener();
        getServer().getPluginManager().registerEvents(playerListener,this);
    }

    public static AttributeAdditionAll getInstance() {
        return instance;
    }
}
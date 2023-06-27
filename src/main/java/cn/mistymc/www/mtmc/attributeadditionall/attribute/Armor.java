package cn.mistymc.www.mtmc.attributeadditionall.attribute;

import cn.mistymc.www.mtmc.attributeadditionall.AttributeAdditionAll;
import github.saukiya.sxattribute.data.attribute.AttributeType;
import github.saukiya.sxattribute.data.attribute.SubAttribute;
import github.saukiya.sxattribute.data.eventdata.EventData;
import github.saukiya.sxattribute.data.eventdata.sub.DamageData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

/**
 * 护甲
 */
public class Armor extends SubAttribute {
    public Armor() {
        super((JavaPlugin) AttributeAdditionAll.getInstance(), 1, AttributeType.OTHER);
    }

    protected YamlConfiguration defaultConfig(YamlConfiguration config) {
        config.set("Armor.DiscernName", "护甲");
        config.set("Armor.CombatPower", 1);
        return config;
    }

    public void eventMethod(double[] values, EventData eventData) {
        DamageData damageData = (DamageData) eventData;
        double armor = values[0];
        //免伤比率
        double ratioOfFreeDamage = armor / (armor + 10000);
        //Bukkit.getLogger().info("免伤比率 : " + ratioOfFreeDamage);
        //怪物伤害*免伤比率
        damageData.setDamage(damageData.getDamage() * ratioOfFreeDamage);
    }

    public Object getPlaceholder(double[] values, Player player, String s) {
        return s.equals(getName()) ? values[0] : null;
    }

    public List<String> getPlaceholders() {
        return Collections.singletonList(getName());
    }

    public void loadAttribute(double[] doubles, String s) {
        if (s.contains(getString("Armor.DiscernName")))
            doubles[0] = doubles[0] + getNumber(s);
    }
}


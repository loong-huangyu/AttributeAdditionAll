package cn.mistymc.www.mtmc.attributeadditionall.attribute;

import cn.mistymc.www.mtmc.attributeadditionall.AttributeAdditionAll;
import github.saukiya.sxattribute.data.attribute.AttributeType;
import github.saukiya.sxattribute.data.attribute.SubAttribute;
import github.saukiya.sxattribute.data.eventdata.EventData;
import github.saukiya.sxattribute.data.eventdata.sub.UpdateData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

/**
 * 移速加成
 */
public class SpeedBonus extends SubAttribute {

    public SpeedBonus() {
        super((JavaPlugin) AttributeAdditionAll.getInstance(), 1, AttributeType.UPDATE);
    }

    protected YamlConfiguration defaultConfig(YamlConfiguration config) {
        config.set("SpeedBonus.DiscernName", "移速加成");
        //默认值
        config.set("SpeedBonus.Default", 0.2D);
        //战斗力加成
        config.set("SpeedBonus.CombatPower", 1);
        return config;
    }

    public void eventMethod(double[] values, EventData eventData) {
        if (eventData instanceof UpdateData && ((UpdateData) eventData).getEntity() instanceof Player) {
            Player player = (Player) ((UpdateData) eventData).getEntity();
            double agile = values[0];
            //Bukkit.getLogger().info("系统值: " + agile);
            //移速加成 相对基础数率扩大多少倍 addition = agile * 倍率 策划定义
            float walkSpeed = (float) (config().getDouble("SpeedBonus.Default") + agile * 0.1 / (400.0D + agile));
            //Bukkit.getLogger().info("设置值: " + walkSpeed);
            //Bukkit.getLogger().info("提升率: " + agile * 0.1 / (400.0D + agile) / config().getDouble("SpeedBonus.Default"));
            player.setWalkSpeed(walkSpeed);
        }
    }

    public Object getPlaceholder(double[] values, Player player, String string) {
        return string.equals(getName()) ? values[0] : null;
    }

    public List<String> getPlaceholders() {
        return Collections.singletonList(getName());
    }

    public void loadAttribute(double[] values, String lore) {
        if (lore.contains(getString("SpeedBonus.DiscernName")))
            values[0] = values[0] + getNumber(lore);
    }

    /**
     * 正确的值
     *
     * @param values
     */
    public void correct(double[] values) {
        //values[0] = Math.min(Math.max(values[0], -99.0D), config().getInt("SpeedBonus.UpperLimit", 2147483647));
    }

    public double calculationCombatPower(double[] values) {
        return values[0] * config().getInt("SpeedBonus.CombatPower");
    }
}

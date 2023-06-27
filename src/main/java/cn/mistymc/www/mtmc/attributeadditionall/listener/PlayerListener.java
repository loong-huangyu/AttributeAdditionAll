package cn.mistymc.www.mtmc.attributeadditionall.listener;

import github.saukiya.sxattribute.SXAttribute;
import github.saukiya.sxattribute.data.attribute.SXAttributeData;
import github.saukiya.sxattribute.data.eventdata.sub.DamageData;
import github.saukiya.sxattribute.event.SXDamageEvent;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener {

    public PlayerListener() {
    }

    @EventHandler
    public void onSXDamageEvent(SXDamageEvent event) {
        //由于mm的怪物技能伤害不受sx改变，于是靠取消伤害和改变血量来模拟
        //防御者
        LivingEntity defender = event.getData().getDefender();
        //攻击者
        LivingEntity attacker = event.getData().getAttacker();
        DamageData damageData = event.getData();
        double d = damageData.getDamage();
        if (attacker instanceof Player && defender instanceof Player) {
            //玩家打玩家
            Player p = (Player) defender;
            //获取配置的属性
            SXAttributeData data = SXAttribute.getAttributeManager().getEntityData(p);
            double armor = data.getValues("Armor")[0];
            if (armor != 0) {
                //TODO 护甲影响d
                //免伤比率
                double ratioOfFreeDamage = armor / (armor + 10000);
               // Bukkit.getLogger().info("玩家间免伤比率 : " + ratioOfFreeDamage);
                //怪物伤害*免伤比率
                d = damageData.getDamage() * (1 - ratioOfFreeDamage);
                damageData.setCancelled(true);
                p.setHealth(Math.max(0, p.getHealth() - d));
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.2f, 2);
            }
        } else if (defender instanceof Player) {
            //怪物打玩家
            Player p = (Player) defender;
            SXAttributeData data = SXAttribute.getAttributeManager().getEntityData(p);
            double armor = data.getValues("Armor")[0];
            if (armor != 0) {
                //TODO 护甲影响d
                //免伤比率
                double ratioOfFreeDamage = armor / (armor + 10000);
                //Bukkit.getLogger().info("受怪物攻击基础伤害" + damageData.getDamage());
                //Bukkit.getLogger().info("免伤比率" + ratioOfFreeDamage);
                //怪物伤害*(1-免伤比率)
                d = damageData.getDamage() * (1 - ratioOfFreeDamage);
                //Bukkit.getLogger().info("受怪物攻击伤害 : " + damageData.getDamage() * (1 - ratioOfFreeDamage));
                damageData.setCancelled(true);
                p.setHealth(Math.max(0, p.getHealth() - d));
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.2f, 2);
            }
        }
    }

}

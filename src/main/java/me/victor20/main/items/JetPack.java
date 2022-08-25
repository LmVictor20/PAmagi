package me.victor20.main.items;

import me.victor20.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class JetPack implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {

        //если ентити это игрок
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            //ItemStack itemInMainHand = (p.getInventory().getItemInMainHand());
            if (p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("num"), PersistentDataType.INTEGER)) {

                // if (p.getInventory().getItemInMainHand().getType() == Material.RED_MUSHROOM) {
                //если причина домага это падение
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    //отключение ивента
                    event.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    //собыете взаемодействия
    public void jetpackClick(PlayerInteractEvent event) {
        //обозначаем плеера
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has
                (NamespacedKey.fromString("num"), PersistentDataType.INTEGER)) {

            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

                player.setVelocity(player.getLocation().getDirection());
                Bukkit.getScheduler().runTaskTimer(Main.INSTACE, new BukkitRunnable() {
                    private int count = 0;
                    @Override

                    public void run() {

                        count++;

                        player.setVelocity(player.getLocation().getDirection());

                        if (count>5) {
                            cancel();
                        }
                    }, 1 , 1);
                Player play = event.getPlayer();

                play.playSound(play.getLocation(), Sound.ENTITY_GHAST_SHOOT, 2.0F, 2.0F);
                play.updateInventory();
                }
            }
        }

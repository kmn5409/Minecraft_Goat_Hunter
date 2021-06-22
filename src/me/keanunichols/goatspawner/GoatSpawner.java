package me.keanunichols.goatspawner;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.entity.Goat;


public class GoatSpawner extends JavaPlugin {
	
	private BukkitTask task;
	private GoatSpawner instance;
	private Random random;
	
	private Random getRandom() {
        return this.random;
    }
    
    public int getRandomCount(final int max) {
        final boolean negative = this.getRandom().nextBoolean();
        final int num = this.getRandom().nextInt(max);
        return negative ? (-num) : num;
    }
    

	
	@Override
	public void onEnable() {
		this.random = new Random();
		//JavaPlugin plugin = this;
		this.instance = this;
		
		this.task = new BukkitRunnable() {
			
            int goatCount = 20;
            
            
            
            public void run() {
            	if(Bukkit.getOnlinePlayers().size() == 0)
            		return;
            	/*
            	for(Player plr: Bukkit.getOnlinePlayers()) {
    	        	plr.sendMessage("screaming");
    	        }
    	        */
            	int randomSize = GoatSpawner.this.getRandom().nextInt(Bukkit.getOnlinePlayers().size());
                final Player onlinePlayer = new ArrayList<Player>(Bukkit.getOnlinePlayers()).get(randomSize);
                final Set<Goat> goatSet = new HashSet<Goat>();
                for (int i = 0; i < this.goatCount; ++i) {
                	final World pWld = onlinePlayer.getWorld();
                    final Goat goat = (Goat)pWld.spawnEntity(onlinePlayer.getLocation().clone().add((double)(GoatSpawner.this.getRandomCount(3) + 1), 0.0, (double)(GoatSpawner.this.getRandomCount(3) + 1)), EntityType.GOAT);
                    goat.setScreaming(true);
                    goat.setTarget(onlinePlayer);
                    goat.attack(onlinePlayer);
                    goatSet.add(goat);
                }
                new BukkitRunnable() {
                    public void run() {
                    	//int num = 0;
                        for (final Goat goat : goatSet) {
                            goat.remove();
                            //num+=1;
                        }
                        /*
                        for(Player plr: Bukkit.getOnlinePlayers()) {
            	        	plr.sendMessage("removing " + num + " goats");
            	        }
            	        */
                    }
                }.runTaskLater((Plugin)GoatSpawner.this.instance, 2400L);
            }
        }.runTaskTimer((Plugin)this, 1200L, 1200L);
        
	}

}

package me.whackyorb.trapBlocker;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TrapBlocker extends JavaPlugin implements Listener {
	public void onEnable() {
		//Register events
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable() {
		//nothing
	}
	
	private void untrapPortalBlock(final Block b){
		if (b.getType() == Material.PORTAL){
			if (b.getData() == 1){
				if (!b.getRelative(BlockFace.NORTH).isEmpty() || !b.getRelative(BlockFace.SOUTH).isEmpty()){
					b.breakNaturally();
				}
			}
			else {
				if (!b.getRelative(BlockFace.EAST).isEmpty() || !b.getRelative(BlockFace.WEST).isEmpty()){
					b.breakNaturally();
				}
			}
		}
	}
	
	@EventHandler
	public void onPortalCreate(final PortalCreateEvent e) {
		new BukkitRunnable() {
            @Override
            public void run() {
            	for(Block b:e.getBlocks()){
            		untrapPortalBlock(b);
            	}
            }
        }.runTaskLater(this, 1);
	}
	
	@EventHandler
	public void onPhysics(BlockPhysicsEvent e) {
		untrapPortalBlock(e.getBlock());
	}
}

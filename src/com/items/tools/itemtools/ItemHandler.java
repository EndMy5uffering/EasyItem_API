package com.items.tools.itemtools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemHandler implements Listener {
	
	private static Set<EasyItem> easyItems = new HashSet<EasyItem>();
	
	public static void add(EasyItem... items) {
		easyItems.addAll(Arrays.asList(items));
	}
	
	public static EasyItem getEasyItemWithItem(ItemStack item) {
		for(EasyItem i : easyItems) {
			if(i.equals((Object)item)) {
				return i;
			}
		}
		return null;
	}
	
	@EventHandler
	public void OnPlayerInteract(PlayerInteractEvent e) {
		EasyItem i = ItemHandler.getEasyItemWithItem(e.getItem());
		if(i == null) return;
		e.setCancelled(true);
		
		if(i.getAction() != null && i.hasAcces(e.getPlayer()) && EasyItem.isValidItem(e.getItem())) i.getAction().apply(e);
	}

	public static Set<EasyItem> getEasyItems() {
		return easyItems;
	}
	
}

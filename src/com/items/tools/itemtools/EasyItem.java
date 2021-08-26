package com.items.tools.itemtools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EasyItem {

	private final ItemStack item;
	private final ItemAction action;
	private Set<String> Permissions = new HashSet<String>();

	private final String code;
	private final String codeName;
	
	public EasyItem(ItemHandler handler, String name, Material m, List<String> lore, ItemAction action) {
		item = setNbtTag(getItem(name, lore, m), handler.getItemCodeName(), handler.getItemCode());
		this.action = action;
		this.code = handler.getItemCode();
		this.codeName = handler.getItemCodeName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ItemStack) && !(obj instanceof EasyItem)) return false;
		ItemStack other;

		if(obj instanceof ItemStack) {
			other = (ItemStack)obj;
		}else {
			other = ((EasyItem)obj).getItem(); 
		}

		if(!other.hasItemMeta()) return false;

		ItemMeta metaOther = other.getItemMeta();
		ItemMeta myMeta = item.getItemMeta();
		return (item.getType().equals(other.getType())
				&& myMeta.getDisplayName().equals(metaOther.getDisplayName()));
	}
	
	public static String getNbtTag(ItemStack item, String tag) {
		net.minecraft.world.item.ItemStack nbtItem = org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asNMSCopy(item);
		net.minecraft.nbt.NBTTagCompound nbtcomp = nbtItem.getTag();
		return nbtcomp == null ? "" : nbtcomp.getString(tag);
	}
	
	public static ItemStack setNbtTag(ItemStack item, String tag, String value) {
		net.minecraft.world.item.ItemStack nbtItem = org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asNMSCopy(item);
		net.minecraft.nbt.NBTTagCompound nbtcomp = (nbtItem.hasTag()) ? nbtItem.getTag() : new net.minecraft.nbt.NBTTagCompound();
		nbtcomp.set(tag, net.minecraft.nbt.NBTTagString.a(value));
		nbtItem.setTag(nbtcomp);
		return org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack.asBukkitCopy(nbtItem);
	}
	
	public static ItemStack getItem(String name, List<String> lore, Material m) {
		ItemStack out = new ItemStack(m);
		ItemMeta meta = out.getItemMeta();
		meta.setLore(lore);
		meta.setDisplayName(name);
		out.setItemMeta(meta);
		return out;
	}
	
	public void addPermissions(String... permissions) {
		for(String s : permissions) {
			this.Permissions.add(s);
		}
	}
	
	public boolean hasAcces(Player p) {
		if(Permissions.size() == 0) return true;
		for(String s : Permissions) {
			if(p.hasPermission(s)) return true;
		}
		return false;
	}
	
	public ItemStack getItem() {
		return item;
	}

	public ItemAction getAction() {
		return action;
	}

	public Set<String> getPermissions() {
		return Permissions;
	}
	
	
	
}

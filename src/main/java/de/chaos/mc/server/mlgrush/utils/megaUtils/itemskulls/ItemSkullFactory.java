package de.chaos.mc.server.mlgrush.utils.megaUtils.itemskulls;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.UUID;

public class ItemSkullFactory {

    private final Base64 base64 = new Base64();
    Reflections itemSkullReflections = new Reflections();

    public ItemStack createItemSkull(String url, String displayName, String... lore) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), UUID.randomUUID().toString());
        PropertyMap propertyMap = profile.getProperties();
        byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        propertyMap.put("textures", new Property("textures", new String(encodedData)));
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        ItemMeta headMeta = head.getItemMeta();
        Class<?> headMetaClass = headMeta.getClass();
        itemSkullReflections.getField(headMetaClass, "profile", GameProfile.class).set(headMeta, profile);

        headMeta.setDisplayName(displayName);
        headMeta.setLore(Arrays.asList(lore));

        head.setItemMeta(headMeta);

        return head;
    }

    public ItemStack getPlayerSkull(String name, String displayName, String... lore) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(name);

        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));

        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
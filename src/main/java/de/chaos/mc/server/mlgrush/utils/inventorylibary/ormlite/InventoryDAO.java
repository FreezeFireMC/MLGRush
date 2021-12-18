package de.chaos.mc.server.mlgrush.utils.inventorylibary.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DatabaseTable(tableName = "MLGRushInvSorting")
public class InventoryDAO {
    public static final String UUID_FIELD = "UUID";
    public static final String STICK_SLOT = "STICK_SLOT";
    public static final String PICKAXE_FIELD = "PICKAXE_SLOT";
    public static final String SANDSTONE_FIELD = "SANDSTONE_SLOT";

    @DatabaseField(id = true, columnName = UUID_FIELD)
    public UUID uuid;

    @DatabaseField(columnName = STICK_SLOT)
    public int stickSlot;

    @DatabaseField(columnName = PICKAXE_FIELD)
    public int pickaxeSlot;

    @DatabaseField(columnName = SANDSTONE_FIELD)
    public int sandstoneSlot;
}
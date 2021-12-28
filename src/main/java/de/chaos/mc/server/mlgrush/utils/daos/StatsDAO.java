package de.chaos.mc.server.mlgrush.utils.daos;

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
@DatabaseTable(tableName = "MLGRushStats")
public class StatsDAO {
    public static final String UUID_FIELD = "UUID";
    public static final String KILLS_FIELD = "KILLS";
    public static final String DEATHS_FIELD = "DEATHS";
    public static final String BED_FIELD = "BROKEN_BEDS";
    public static final String WIN_FIELD = "WINS";

    @DatabaseField(id = true, columnName = UUID_FIELD)
    public UUID uuid;

    @DatabaseField(columnName = KILLS_FIELD)
    public long kills;

    @DatabaseField(columnName = DEATHS_FIELD)
    public long deaths;

    @DatabaseField(columnName = BED_FIELD)
    public long brokenBeds;
    @DatabaseField(columnName = WIN_FIELD)
    public long wins;
}

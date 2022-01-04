package de.chaos.mc.server.mlgrush.utils.statsLibary;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.server.mlgrush.utils.daos.StatsDAO;
import de.chaos.mc.serverapi.utils.daos.DAOManager;

import java.sql.SQLException;
import java.util.UUID;

public class StatsRepository implements StatsInterface {
    public JdbcPooledConnectionSource connectionSource;
    public DAOManager<StatsDAO, UUID> daoManager;

    public StatsRepository(JdbcPooledConnectionSource jdbcPooledConnectionSource) {
        this.connectionSource = jdbcPooledConnectionSource;
        this.daoManager = new DAOManager<StatsDAO, UUID>(StatsDAO.class, connectionSource);
    }

    @Override
    public void addKills(UUID uuid, long amount) {
        StatsDAO statsDAO = null;
        try {
            statsDAO = daoManager.getDAO().queryForId(uuid);
            if (statsDAO != null) {
                statsDAO.setKills(statsDAO.getKills() + amount);
            } else {
                statsDAO = StatsDAO.builder()
                        .uuid(uuid)
                        .wins(0)
                        .brokenBeds(0)
                        .deaths(0)
                        .kills(amount)
                        .build();
            }
            daoManager.getDAO().createOrUpdate(statsDAO);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void addDeaths(UUID uuid, long amount) {
        StatsDAO statsDAO = null;
        try {
            statsDAO = daoManager.getDAO().queryForId(uuid);
            if (statsDAO != null) {
                statsDAO.setDeaths(statsDAO.getKills() + amount);
            } else {
                statsDAO = StatsDAO.builder()
                        .uuid(uuid)
                        .wins(0)
                        .brokenBeds(0)
                        .deaths(amount)
                        .kills(0)
                        .build();
            }
            daoManager.getDAO().createOrUpdate(statsDAO);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void addBrokenBeads(UUID uuid, long amount) {
        StatsDAO statsDAO = null;
        try {
            statsDAO = daoManager.getDAO().queryForId(uuid);
            if (statsDAO != null) {
                statsDAO.setBrokenBeds(statsDAO.getKills() + amount);
            } else {
                statsDAO = StatsDAO.builder()
                        .uuid(uuid)
                        .wins(0)
                        .brokenBeds(amount)
                        .deaths(0)
                        .kills(0)
                        .build();
            }
            daoManager.getDAO().createOrUpdate(statsDAO);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public long getKills(UUID uuid) {
        StatsDAO statsDAO = null;
        try {
            statsDAO = daoManager.getDAO().queryForId(uuid);
            if (statsDAO != null) {
                return statsDAO.getKills();
            } else {
                return 0;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    @Override
    public long getDeaths(UUID uuid) {
        StatsDAO statsDAO = null;
        try {
            statsDAO = daoManager.getDAO().queryForId(uuid);
            if (statsDAO != null) {
                return statsDAO.getDeaths();
            } else {
                return 0;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    @Override
    public long getBrokenBed(UUID uuid) {
        StatsDAO statsDAO = null;
        try {
            statsDAO = daoManager.getDAO().queryForId(uuid);
            if (statsDAO != null) {
                return statsDAO.getBrokenBeds();
            } else {
                return 0;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    @Override
    public void addWin(UUID uuid, long amount) {
        StatsDAO statsDAO = null;
        try {
            statsDAO = daoManager.getDAO().queryForId(uuid);
            if (statsDAO != null) {
                statsDAO.setWins(statsDAO.getKills() + amount);
            } else {
                statsDAO = StatsDAO.builder()
                        .uuid(uuid)
                        .wins(amount)
                        .brokenBeds(0)
                        .deaths(0)
                        .kills(0)
                        .build();
            }
            daoManager.getDAO().createOrUpdate(statsDAO);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public long getWin(UUID uuid) {
        StatsDAO statsDAO = null;
        try {
            statsDAO = daoManager.getDAO().queryForId(uuid);
            if (statsDAO != null) {
                return statsDAO.getWins();
            } else {
                return 0;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }
}

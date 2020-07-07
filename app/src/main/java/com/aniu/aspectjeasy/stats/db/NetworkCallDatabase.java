package com.aniu.aspectjeasy.stats.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {NetworkCallEntity.class}, version = NetworkCallDBManager.DB_VERSION, exportSchema = false)
public abstract class NetworkCallDatabase extends RoomDatabase {

    public abstract NetworkCallDao getNetworkCallDao();
}

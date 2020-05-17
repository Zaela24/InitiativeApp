// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {

    //TODO( Write doc comments )
    abstract val characterDao: CharacterDao

    companion object {

        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        fun getInstance(context: Context): CharacterDatabase{

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CharacterDatabase::class.java,
                        "character_database"
                    )
                        // This database is only designed to temporarily hold information for at
                        // most every few uses of the app since users would only need to save
                        // initiative rolls if they migrated away from the app during a fight
                        // in a Pathfinder or similar table top game. As such, there is no reason
                        // to create any sort of migration protocol, therefore it will fallback to
                        // destructive migration.
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
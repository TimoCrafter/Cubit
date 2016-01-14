package de.keks.internal.core.tasks;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.ConfigValues;
import de.keks.internal.I18n;
import de.keks.internal.SkyConfig;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class SetupLanguageTask implements Runnable {

    private CubitPlugin cubit;

    public SetupLanguageTask(CubitPlugin cubit) {
        this.cubit = cubit;
    }

    @Override
    public void run() {
        cubit.getServer().getScheduler().runTaskAsynchronously(cubit, new Runnable() {
            @Override
            public void run() {
                new I18n(cubit, SkyConfig.getString(ConfigValues.pluginLocale));
            }
        });

    }

}

package me.StevenLawson.TotalFreedomMod;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TFM_Heartbeat implements Runnable
{
    private TotalFreedomMod plugin;

    TFM_Heartbeat(TotalFreedomMod instance)
    {
        this.plugin = instance;
    }

    @Override
    public void run()
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            TFM_UserInfo playerdata = TFM_UserInfo.getPlayerData(p, plugin);
            playerdata.resetMsgCount();
            playerdata.resetBlockDestroyCount();
            playerdata.resetBlockPlaceCount();
        }

        if (plugin.autoEntityWipe)
        {
            TFM_Util.wipeDropEntities(!plugin.allowExplosions);
        }

        if (plugin.disableNight)
        {
            for (World world : Bukkit.getWorlds())
            {
                if (world.getTime() > 12000L)
                {
                    TFM_Util.setWorldTime(world, 1000L);
                }
            }
        }
        
        if (plugin.disableWeather)
        {
            for (World world : Bukkit.getWorlds())
            {
                if (world.getWeatherDuration() > 0)
                {
                    world.setThundering(false);
                    world.setWeatherDuration(0);
                }
                else if (world.getThunderDuration() > 0)
                {
                    world.setStorm(false);
                    world.setThunderDuration(0);
                }
            }
        }
    }
}

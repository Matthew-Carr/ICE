package main;

import org.json.JSONException;
import org.json.JSONObject;

import itemBuilder.ItemBuilder;

public class ChampionStats {

    // regenerating stats
    public Double health = 0d;
    public Double health_regeneration = 0d;
    public Double resources = 0d;
    public Double resource_regeneration = 0d;

    // basic combat stats
    public Double attack_damage = 0d;
    public Double ability_power = 0d;
    public Double attack_range = 0d;
    public Double attack_speed = 0d;
    public Double critical_chance = 0d;
    public Double critical_damage = 200d;
    public Double movement_speed = 0d;
    public Double cooldown_reduction = 0d;

    // defense stats
    public Double armor = 0d;
    public Double magic_resistance = 0d;
    public Double tenacity = 0d;
    public Double life_steal = 0d;
    public Double spell_vamp = 0d;

    // penetration stats
    public Double armor_penetration__flat = 0d;
    public Double armor_penetration__percent = 0d;
    public Double magic_penetration__flat = 0d;
    public Double magic_penetration__percent = 0d;

    public ChampionStats() {
        // uh nothing to do here
    }

    public Boolean setLevel(ChampionObj caller, JSONObject api_result, int level) throws JSONException {
        JSONObject data = api_result.getJSONObject("data");
        for (int i = 0; i < data.names().length(); i++) {
            Object obj = data.get(data.names().getString(i));
            JSONObject jobj = new JSONObject(obj.toString());
            if (jobj.getString("name").toLowerCase().equals(caller.name.toLowerCase())) {
                JSONObject stats = jobj.getJSONObject("stats");
                this.armor += level * stats.getDouble("armorperlevel");
                this.attack_damage += level * stats.getDouble("attackdamageperlevel");
                this.attack_speed += level * stats.getDouble("attackspeedperlevel");
                this.critical_chance += level * stats.getDouble("critperlevel");
                this.health += level * stats.getDouble("hpperlevel");
                this.health_regeneration += level * stats.getDouble("hpregenperlevel");
                this.resources += level * stats.getDouble("mpperlevel");
                this.resource_regeneration += level * stats.getDouble("mpregenperlevel");
                this.magic_resistance += level * stats.getDouble("spellblockperlevel");
                return true;
            }
        }
        return false;
    }

    public Boolean set(ChampionObj caller, JSONObject api_result) throws JSONException {
        JSONObject data = api_result.getJSONObject("data");

        for (int i = 0; i < data.names().length(); i++) {
            Object obj = data.get(data.names().getString(i));
            JSONObject jobj = new JSONObject(obj.toString());
            if (jobj.getString("name").toLowerCase().equals(caller.name.toLowerCase())) {
                JSONObject stats = jobj.getJSONObject("stats");
                this.attack_range = stats.getDouble("attackrange");
                this.resources = stats.getDouble("mp");
                this.resource_regeneration = stats.getDouble("mpregen");
                this.health = stats.getDouble("hp");
                this.health_regeneration = stats.getDouble("hpregen");
                this.magic_resistance = stats.getDouble("spellblock");
                this.movement_speed = stats.getDouble("movespeed");
                this.armor = stats.getDouble("armor");
                this.attack_damage = stats.getDouble("attackdamage");
                this.critical_chance = stats.getDouble("crit");
                this.ability_power = 0d;

                //attack speed is calculated as 0.625/(1+attackspeedoffset)
                Double attackspeedoffset = stats.getDouble("attackspeedoffset");
                this.attack_speed = 0.625 / (1 + attackspeedoffset);
                return true;
            }
        }
        return false;
    }

    public void addItem(ItemBuilder caller_class, ChampionObj caller, String item_name) throws JSONException {
        JSONObject item = null;
        JSONObject result = caller_class.item;
        JSONObject data = result.getJSONObject("data");

        for (int i = 0; i < data.names().length(); i++) {
            Object obj = data.get(data.names().getString(i));
            JSONObject jobj = new JSONObject(obj.toString());
            if (jobj.getString("name").equals(item_name)) {
                item = jobj;
                break;
            }
        }

        if (item != null) {
            JSONObject stats = item.getJSONObject("stats");
            if (!Double.isNaN(stats.optDouble("FlatArmorMod"))) {
                caller.stats.armor += stats.optDouble("FlatArmorMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatAttackSpeedMod"))) {
                caller.stats.attack_speed += caller.stats.attack_speed * stats.optDouble("FlatAttackSpeedMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatCritChanceMod"))) {
                caller.stats.critical_chance += stats.optDouble("FlatCritChanceMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatCritDamageMod"))) {
                caller.stats.critical_damage = stats.optDouble("FlatCritDamageMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatMPPoolMod"))) {
                caller.stats.resources += stats.optDouble("FlatMPPoolMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatMPRegenMod"))) {
                caller.stats.resource_regeneration += stats.optDouble("FlatMPRegenMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatMagicDamageMod"))) {
                caller.stats.ability_power += stats.optDouble("FlatMagicDamageMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatMovementSpeedMod"))) {
                caller.stats.movement_speed += stats.optDouble("FlatMovementSpeedMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatPhysicalDamageMod"))) {
                caller.stats.attack_damage += stats.optDouble("FlatPhysicalDamageMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatSpellBlockMod"))) {
                caller.stats.magic_resistance += stats.optDouble("FlatSpellBlockMod");
            }
            if (!Double.isNaN(stats.optDouble("PercentArmorMod"))) {
                caller.stats.armor += caller.stats.armor * stats.optDouble("PercentArmorMod");
            }
            if (!Double.isNaN(stats.optDouble("PercentAttackSpeedMod"))) {
                caller.stats.attack_speed += caller.stats.attack_speed * stats.optDouble("PercentAttackSpeedMod");
            }
            if (!Double.isNaN(stats.optDouble("PercentHPPoolMod"))) {
                caller.stats.health += caller.stats.health * stats.optDouble("PercentHPPoolMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatHPPoolMod"))) {
                caller.stats.health += stats.optDouble("FlatHPPoolMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatHPRegenMod"))) {
                caller.stats.health_regeneration += stats.optDouble("FlatHPRegenMod");
            }
            if (!Double.isNaN(stats.optDouble("PercentLifeStealMod"))) {
                caller.stats.life_steal *= (1 + stats.optDouble("PercentLifeStealMod"));
            }
            if (!Double.isNaN(stats.optDouble("FlatEnergyPoolMod"))) {
                caller.stats.resources += stats.optDouble("FlatEnergyPoolMod");
            }
            if (!Double.isNaN(stats.optDouble("FlatEnergyRegenMod"))) {
                caller.stats.resource_regeneration += stats.optDouble("FlatEnergyRegenMod");
            }
            if (!Double.isNaN(stats.optDouble("PercentCritChanceMod"))) {
                caller.stats.critical_chance *= (1 + stats.optDouble("PercentCritChanceMod"));
            }
            if (!Double.isNaN(stats.optDouble("PercentCritDamageMod"))) {
                caller.stats.critical_damage *= (1 + stats.optDouble("PercentCritDamageMod"));
            }
            if (!Double.isNaN(stats.optDouble("PercentHPRegenMod"))) {
                caller.stats.resource_regeneration *= (1 + stats.optDouble("PercentHPRegenMod"));
            }
            if (!Double.isNaN(stats.optDouble("PercentMPPoolMod"))) {
                caller.stats.resources *= (1 + stats.optDouble("PercentMPPoolMod"));
            }
            if (!Double.isNaN(stats.optDouble("PercentMPRegenMod"))) {
                caller.stats.resource_regeneration *= (1 + stats.optDouble("PercentMPRegenMod"));
            }
            if (!Double.isNaN(stats.optDouble("PercentMagicDamageMod"))) {
                caller.stats.ability_power *= (1 + stats.optDouble("PercentMagicDamageMod"));
            }

            System.out.println("movement_speed is " + caller.stats.movement_speed);
            System.out.println("stat is " + (1.0 + stats.optDouble("PercentMovementSpeedMod")));
            if (!Double.isNaN(stats.optDouble("PercentMovementSpeedMod"))) {
                caller.stats.movement_speed *= (1.0 + stats.optDouble("PercentMovementSpeedMod"));
            }
            if (!Double.isNaN(stats.optDouble("PercentPhysicalDamageMod"))) {
                caller.stats.attack_damage *= (1 + stats.optDouble("PercentPhysicalDamageMod"));
            }
            if (!Double.isNaN(stats.optDouble("PercentSpellVampMod"))) {
                caller.stats.spell_vamp *= (1 + stats.optDouble("PercentSpellVampMod"));
            }
            if (!Double.isNaN(stats.optDouble("PercentSpellBlockMod"))) {
                caller.stats.magic_resistance *= (1 + stats.optDouble("PercentSpellBlockMod"));
            }

            caller_class.displayStats();
        }
    }
}

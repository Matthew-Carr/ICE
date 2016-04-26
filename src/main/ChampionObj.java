package main;

import itemBuilder.ChampionStat;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import api.*;
import connection.*;

public class ChampionObj {

    public String name;
    public ChampionStats stats;
    public Integer level = 1;

    public ChampionObj(String _name) {
        this.name = _name;
        stats = new ChampionStats();
    }

    public void setOfficialBaseStats(JSONObject data, TextArea textarea) throws JSONException {
        stats.set(this, data);
        textarea.setText(this.display());
    }

    public void setLevel(JSONObject data, int level) throws JSONException {
        stats.setLevel(this, data, level);
    }

    /*
     * Display this instance's data
     */
    public String display() {
        StringBuilder builder = new StringBuilder();

        String[] labels = new String[22];
        labels[0] = ChampionStat.NAME.toString();
        labels[1] = ChampionStat.HEALTH.toString();
        labels[2] = ChampionStat.HEALTH_REGENERATION.toString();
        labels[3] = ChampionStat.RESOURCES.toString();
        labels[4] = ChampionStat.RESOURCE_REGENERATION_PER_5.toString();
        labels[5] = ChampionStat.ATTACK_DAMAGE.toString();
        labels[6] = ChampionStat.ABILITY_POWER.toString();
        labels[7] = ChampionStat.ATTACK_RANGE.toString();
        labels[8] = ChampionStat.ATTACK_SPEED.toString();
        labels[9] = ChampionStat.CRIT_CHANCE.toString();
        labels[10] = ChampionStat.CRIT_DAMAGE.toString();
        labels[11] = ChampionStat.MOVEMENT_SPEED.toString();
        labels[12] = ChampionStat.COOLDOWN_REDUCTION.toString();
        labels[13] = ChampionStat.ARMOR.toString();
        labels[14] = ChampionStat.MAGIC_RESISTANCE.toString();
        labels[15] = ChampionStat.TENACITY.toString();
        labels[16] = ChampionStat.LIFE_STEAL.toString();
        labels[17] = ChampionStat.SPELL_VAMP.toString();
        labels[18] = ChampionStat.ARMOR_PEN_FLAT.toString();
        labels[19] = ChampionStat.ARMOR_PEN_PERCENT.toString();
        labels[20] = ChampionStat.MAGIC_PEN_FLAT.toString();
        labels[21] = ChampionStat.MAGIC_PEN_PERCENT.toString();

        String[] values = new String[22];
        values[0] = this.name;
        values[1] = stats.health.toString();
        values[2] = stats.health_regeneration.toString();
        values[3] = stats.resources.toString();
        values[4] = stats.resource_regeneration.toString();
        values[5] = stats.attack_damage.toString();
        values[6] = stats.ability_power.toString();
        values[7] = stats.attack_range.toString();
        values[8] = stats.attack_speed.toString();
        values[9] = stats.critical_chance.toString();
        values[10] = stats.critical_damage.toString();
        values[11] = stats.movement_speed.toString();
        values[12] = stats.cooldown_reduction.toString();
        values[13] = stats.armor.toString();
        values[14] = stats.magic_resistance.toString();
        values[15] = stats.tenacity.toString();
        values[16] = stats.life_steal.toString();
        values[17] = stats.spell_vamp.toString();
        values[18] = stats.armor_penetration__flat.toString();
        values[19] = stats.armor_penetration__percent.toString();
        values[20] = stats.magic_penetration__flat.toString();
        values[21] = stats.magic_penetration__percent.toString();

        // formatting so that the columns line up
        Integer longest = findLongestStr(labels);
        Integer offset = 5;
        Integer diff = 0;
        for (int i = 0; i < 21; i++) {
            diff = longest - labels[i].length();
            diff += offset;
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < diff; j++) {
                str.append(" ");
            }
            str.append(values[i]);
            builder.append("\n" + labels[i] + ":" + " " + str);
        }
        return builder.toString();
    }

    public static Integer findLongestStr(String[] text) {
        Integer max = 0;
        for (String word : text) {
            if (word.length() > max)
                max = word.length();
        }
        return max;
    }

    public String formatString(String label, String value, int longest) {
        int max_length = 90;
        Integer len = max_length - (label.length() + 3) - value.length() + (longest - label.length());
        System.out.println("label len is " + label.length() + " need to add " + len.toString());
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < len; i++) {
            a.append(" ");
        }
        return "\n" + label + ": " + a.toString() + value;
    }
}

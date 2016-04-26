package itemBuilder;

/**
 * Created by MC046006 on 4/22/2016.
 */
public enum ChampionStat {
    NAME("Name"),
    HEALTH("Health"),
    HEALTH_REGENERATION("Health Regeneration"),
    RESOURCES("Resources"),
    RESOURCE_REGENERATION_PER_5("Resource Regeneration/5"),
    ATTACK_DAMAGE("Attack Damage"),
    ABILITY_POWER("Ability Powerino"),
    ATTACK_RANGE("Attack Range"),
    ATTACK_SPEED("Attack Speed"),
    CRIT_CHANCE("Critical Chance"),
    CRIT_DAMAGE("Critical Damage"),
    MOVEMENT_SPEED("Movement Speed"),
    COOLDOWN_REDUCTION("Cooldown Reduction"),
    ARMOR("Armor"),
    MAGIC_RESISTANCE("Magic Resistance"),
    TENACITY("Tenacity"),
    LIFE_STEAL("Life Steal"),
    SPELL_VAMP("Spell Vamp"),
    ARMOR_PEN_FLAT("Armor Penetration Flat"),
    ARMOR_PEN_PERCENT("Armor Penetration Percent"),
    MAGIC_PEN_FLAT("Magic Penetration Flat"),
    MAGIC_PEN_PERCENT("Magic Penetration Percent");

    private final String stat;

    ChampionStat(final String stat) {
        this.stat = stat;
    }

    @Override
    public String toString() {
        return this.stat;
    }
}

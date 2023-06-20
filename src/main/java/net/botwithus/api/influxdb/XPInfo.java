package net.botwithus.api.influxdb;

import net.botwithus.rs3.skills.Skills;
import net.botwithus.rs3.time.Stopwatch;
import net.botwithus.rs3.time.Timer;
import net.botwithus.rs3.time.enums.DurationStringFormat;
import net.botwithus.rs3.util.Math;
import net.botwithus.rs3.util.collection.PairList;
import net.botwithus.rs3.skills.Skill;

public class XPInfo {
    public Skill skillType;

    private int startLvl, startXP, currentLvl, currentXP, xpUntilNextLevel;
    private InfluxNumeric influxLvl, influxXP;

    public XPInfo(Skill skill) {
        this.skillType = skill;
        this.startLvl = skill.getLevel();
        this.startXP = skill.getExperience();
        this.xpUntilNextLevel = skill.getExperienceToNextLevel();
        currentLvl = startLvl;
        currentXP = startXP;
        influxLvl = new InfluxNumeric(currentLvl, currentLvl);
        influxXP = new InfluxNumeric(currentXP, currentXP);
    }

    public void update() {
        skillType = Skills.byId(skillType.getId()).getSkill();
        this.currentLvl = skillType.getLevel();
        this.currentXP = skillType.getExperience();
        this.xpUntilNextLevel = skillType.getExperienceToNextLevel();
    }

    public int getLevelsGained() {
        return currentLvl - startLvl;
    }

    public int getGainedXP() {
        return currentXP - startXP;
    }

    public int getXPHour(Stopwatch watch) {
        return Math.getItemPerHour(watch, getGainedXP());
    }

    public int getSecondsUntilLevel(Stopwatch watch) {
        return (int) ((((double) xpUntilNextLevel) / ((double) getXPHour(watch))) * 3600.0);
    }

    public PairList<String, String> getPairList(Stopwatch stopWatch) {
        PairList<String, String> list = new PairList<>();
        if (currentXP > startXP) {
            list.add(skillType.toString() + " Level: ", currentLvl + " (" + getLevelsGained() + " Gained)");
            list.add(skillType.toString() + " XP Gained: ", getGainedXP() + " (" + getXPHour(stopWatch) + "/Hour)");
            list.add(skillType.toString() + " TTL: ", Timer.secondsToFormattedString(getSecondsUntilLevel(stopWatch), DurationStringFormat.DESCRIPTION));
        }
        return list;
    }

    public Number deriveInfluxXP() {
        update();
        influxXP.setCurrentNum(currentXP);
        return influxXP.getLatest();
    }
}

package models;

import utils.Colors;
import utils.MiExcepcion;

import java.util.*;

public class Worker {
    private static Set<String> skillsDefaults = new HashSet<>();

    private int dni;
    private String name;
    private Set<String> skills;
    private Room room;

    public Worker(int dni, String name, Set<String> skills) throws MiExcepcion{
        setSkillsDefaults();
        this.dni = dni;
        setName(name);
        setSkills(skills);
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setSkills(Set<String> skills) throws MiExcepcion {
        int cont = 0;
        for (String skill : skills) {
            for (String skillDefault : skillsDefaults) {
                if (skill.equals(skillDefault)) {
                    cont++;
                }
            }
        }
        if (cont == skills.size()) {
            this.skills = skills;
        } else {
            throw new MiExcepcion(Colors.RED + "[ Wrong service ]" + Colors.RESET);
        }

    }

    private void setSkillsDefaults() {
        // Skills from Workers
        skillsDefaults.add("mantenimiento");
        skillsDefaults.add("limpieza");
        skillsDefaults.add("piscina");
        skillsDefaults.add("spa");
        skillsDefaults.add("bar");
        skillsDefaults.add("comida");
        skillsDefaults.add("lavanderia");
    }

    public String getName() {
        return name;
    }

    public int getDni() {
        return dni;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        if (room == null) {
            return "== " + getClass().getSimpleName().toUpperCase() + " " + getDni() + " " + getName() + " AVAILABLE ==";
        } else {
            return "== " + getClass().getSimpleName().toUpperCase() + " " + getDni() + " " + getName() + " " + getRoom().getNumRoom() + " ==";
        }
    }
}
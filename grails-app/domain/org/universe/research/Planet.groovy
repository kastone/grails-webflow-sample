package org.universe.research

class Planet implements Serializable{

    String color
    PlanetSize planetSize

    static constraints = {
        color(inList: ["Blue", "Green", "Red"])
    }
}

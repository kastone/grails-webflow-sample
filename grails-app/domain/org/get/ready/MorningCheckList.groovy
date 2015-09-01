package org.get.ready

class MorningCheckList implements Serializable{
    Boolean fallOutOfBed = false
    Boolean dragCombAcrossHead = false
    Boolean drinkCup = false
    Boolean hitAlarm = true


    static constraints = {
    }

    Boolean isComplete(){
        if (fallOutOfBed && dragCombAcrossHead && drinkCup && hitAlarm) { return true }
        return false
    }


}

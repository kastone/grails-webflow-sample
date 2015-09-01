package org.get.ready

class MorningFlowController {

    def index(){
        redirect(action: "morning")
    }

    final morningFlow = {

        init {
            action {
                conversation.morningCheckList = new MorningCheckList()
                conversation.counter = 0L
            }
            on('success').to('executeMorningRoutine')
        }

        executeMorningRoutine {
            on("submit").to "wakeUp"
        }

        somethingAwry {
            render(view: "somethingAwry")
        }

        wakeUp {
            subflow(wakeUpFlow)
            on('somethingAwry').to('somethingAwry')
            on('end').to('groomSelf')
            on('errors').to('executeMorningRoutine')

        }

        groomSelf {
            on("submit") {
                conversation.morningCheckList.dragCombAcrossHead = true
            }.to 'prepareBreakfast'
        }


        prepareBreakfast {
            on("submit") {
                conversation.morningCheckList.drinkCup = true
            }.to 'readyForDay'

        }

        readyForDay {
            action {
                conversation.morningCheckList.save()
                Boolean completed = conversation.morningCheckList.isComplete()
                if (!completed) return error()
            }
            on('success').to('end')
            on('error').to('somethingAwry')//Not handling
        }

        end()

    }
    
    def wakeUpFlow = {
        hitAlarmClock {
            on('submit') {
                conversation.morningCheckList.hitAlarm = true
                [counter:conversation.counter]
            }.to('rollOutOfBed')
            on('increment') {
                //If Running Grails 2.4.5 the execution param is not appended to the wakeUp/hitAlarmClock.gsp increment link.
                //This causes the flow to start over(hits init method)
                //If Running Grails 2.4.3 the execution param IS appended and the flow correctly calls the wakeUpFlow and re-renders the hitAlarmClock.gsp with incremented counter.
                conversation.counter++
                [counter:conversation.counter]
            }.to('hitAlarmClock')
            on('error').to('somethingAwry')
        }
        rollOutOfBed {
            on('submit'){
                conversation.morningCheckList.fallOutOfBed = true
            }.to('end')
        }
        somethingAwry()
        end()
    }

}


package org.universe.research

/*
Not having @Validateable annotation on command object, makes unit tests fail.
 */
class PlanetFlowNoValidateableController {

    def index() {
        redirect (action: "planet")
    }

    def planetFlow ={
        init {
            action {
                flow.planetInstance = new Planet()
                conversation.flowName = 'planet'
            }
            on("success").to "create"
        }
        create {
            on("submit") { WebflowPlanetNoValidateableCommand webflowCommand ->
                //NOTE: Set breakpoint here to see if validates
                webflowCommand.validate()
                if(webflowCommand.hasErrors()){
                    flow.planetInstance.errors = webflowCommand.errors
                    return error()
                }
                flow.planetInstance = webflowCommand.createPlanet()
                flow.planetInstance.save(failOnErrors:true, flush:true)
                return success()
            }.to("show")
        }

        show {
            action {
                flow.planetInstance = Planet.get(params.id)
            }
        }

    }
}
/*
UPDATE: This is not recognized by grails-core as a command object as is it not used as argument to controller action, but just as argument to webflow closure.
The plugin docs suggest it is used as command object. Submit issue to plugin github for clarification.

@Validateable annotation should not be required here according to Grails docs but it seems necessary for Webflow controllers.
 */
class WebflowPlanetNoValidateableCommand implements Serializable{
    //BindUsing not triggered either
    //    @BindUsing({
    //        obj, source -> PlanetSize.findByCode(source['planetSize'])
    //    })
    String color
    String planetSize

    static constraints = {
        color(inList: ["Blue", "Green", "Red"])
    }

    //This is workaround we have since ValueConverters not working properly
    Planet createPlanet(){
        return new Planet(color: this.color, planetSize: PlanetSize.findByCode(this.planetSize))
    }


}



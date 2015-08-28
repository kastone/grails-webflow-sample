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
@Validateable annotation should not be required here according to Grails docs but it seems necessary for Webflow controllers.
If the command object is defined in the same source file as the controller that is using it, Grails will automatically mark it as Validateable. It is not required that command object classes be validateable.
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



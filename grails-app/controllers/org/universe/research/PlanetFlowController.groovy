package org.universe.research

import grails.validation.Validateable

class PlanetFlowController {

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
            on("submit") { WebflowPlanetCommand webflowCommand ->
                webflowCommand.validate()
                if(webflowCommand.hasErrors()){
                    flow.planetInstance.errors = webflowCommand.errors
                    return error()
                }
                flow.planetInstance.color = webflowCommand.color
                flow.planetInstance.planetSize = webflowCommand.planetSize
                flow.planetInstance.save(failOnError:true)
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
@Validateable
class WebflowPlanetCommand implements Serializable{
    //BindUsing not triggered either
    //    @BindUsing({
    //        obj, source -> PlanetSize.findByCode(source['planetSize'])
    //    })
    String color
    PlanetSize planetSize

    static constraints = {
        planetSize(validator: {val, obj, errors ->
            if(!obj.planetSize.description) errors.reject('The planetSize.description property was null. PlanetSize was not bound using CodeValueConverter correctly')
            if(!obj.planetSize.code) errors.reject('The planetSize.code property was null. PlanetSize was not bound using CodeValueConverter correctly')

        })
        color(inList: ["Blue", "Green", "Red"])
    }


}


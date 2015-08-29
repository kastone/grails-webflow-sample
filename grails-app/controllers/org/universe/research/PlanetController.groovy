package org.universe.research

import grails.transaction.Transactional

@Transactional(readOnly = true)
class PlanetController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Planet.list(params), model:[planetInstanceCount: Planet.count()]
    }

    def show() {
        Planet planetInstance = Planet.findById(params.id)
        respond planetInstance
    }

    def create() {
        respond new Planet()
    }

    //This works. If webflow works in unit test too, try geb or integration test.
    @Transactional
    def save(PlanetCommand planetCommand) {
        if (planetCommand.validate()) {
            Planet planetInstance = new Planet()
            planetInstance.color = planetCommand.color
            planetInstance.planetSize = planetCommand.planetSize
            planetInstance.save()
            redirect (action:'show', id: planetInstance.id)
        }else {
            render (view:'create', model: [planetCommand : planetCommand])
        }
    }

}

//NOTE: This doesn't need @Validateable as Grails' docs indicate b/c in same source file as Controller.
class PlanetCommand {
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

package org.universe.research.valueconverter.binding

import grails.test.mixin.*
import org.universe.research.CodeValueConverter
import org.universe.research.Planet
import org.universe.research.PlanetController
import org.universe.research.PlanetSize
import spock.lang.*

@TestFor(PlanetController)
@Mock(Planet)
class PlanetControllerBindsCommandComplexTypeSpec extends Specification {

    static doWithSpring = {
            codeValueConverter(CodeValueConverter)
    }

    void "Test the save action correctly persists an instance"() {
        given:
            PlanetSize.metaClass.static.findByCode = {String code ->
                return new PlanetSize(code: code, description: "TestPlanetSize")
            }

        when:"The save action is executed with an valid command obj"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            params.color = 'Blue'
            params.planetSize = 'Big'//Should be bound with CodeValueConverter
            controller.save()

        then:"The create view is rendered again with the correct model"
            response.redirectedUrl.contains('/planet/show')


    }


    void "Test the save action redirects to create after validation error"() {
        given:
        PlanetSize.metaClass.static.findByCode = {String code ->
            return null
        }

        when:"The save action is executed with an invalid command obj"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        params.color = 'FooBarColor'
        controller.save()

        then:"The create view is rendered again with the correct model"
        view == '/planet/create'
        assert model.planetCommand.hasErrors() == true
        //Correct validation from CodeValueConverter
        //TODO figure why this isn't correctly matching. this is in the string.
        //assert model.planetCommand.errors.allErrors.contains('PlanetCommand.color.inList.error')
        //Would be true if ValueConverter was not working.
        assert !model.planetCommand.errors.allErrors.contains('The planetSize.description property was null. PlanetSize was not bound using CodeValueConverter correctly')
        assert !model.planetCommand.errors.allErrors.contains('The planetSize.code property was null. PlanetSize was not bound using CodeValueConverter correctly')

    }

}

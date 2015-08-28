package org.universe.research.valueconverter.binding

import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.webflow.WebFlowUnitTestMixin
import org.junit.Test
import org.universe.research.CodeValueConverter
import org.universe.research.Planet
import org.universe.research.PlanetFlowController
import org.universe.research.PlanetSize

/*
    This test should check whether a webflow command residing in same source file
    corrrectly can bind a complex object type using ValueConverters. However, there are problems with unit
    testing webflow commands. To see this behavior, run the app and go to /grailsWebflowSample/planetFlow/planet to create a Planet
    You should see 'The planetSize.description property was null. PlanetSize was not bound using CodeValueConverter' which is present if the Command objects properties were not bound propertly.
 */
@TestMixin([WebFlowUnitTestMixin, DomainClassUnitTestMixin])
class PlanetWebflowDoesNotBindCommandComplexTypeControllerTests {


    static doWithSpring = {
            codeValueConverter(CodeValueConverter)
    }

    void 'WebflowCommandObjectUnitTestsNotPlaying'(){
        assert false
    }

    @Test
    void testFlowExecution(){
        mockController(PlanetFlowController)
        planetFlow.init.action()
        assert conversation.flowName == 'planet'
    }

    @Test
    void testWebflowSaveBindsUsingValueConverter() {
        //Followed example here but Command object not bound at all. This is different than running app. So have to run app to see issue of Command object not Binding properly with ValueConverters.
        //https://github.com/grails-plugins/grails-webflow-plugin/blob/master/test/unit/grails/test/mixin/WebFlowUnitTestMixinTests.groovy
        mockDomain(Planet)
        mockCommandObject(PlanetSize)
        mockController(PlanetFlowController)

        PlanetSize.metaClass.static.findByCode = {String code ->
            return new PlanetSize(code: code, description: "TestPlanetSize")
        }

        params.color = 'Blue'
        params.planetSize = 'Big'//Should be bound with CodeValueConverter

        def event = planetFlow.create.on.submit.action()
        //This returns java.lang.NullPointerException: Cannot invoke method validate() on null object. The Command object is not bound.

        assert flow.planetInstance.hasErrors()


    }


//    void "Test the save action redirects to create after validation error"() {
//        given:
//        PlanetSize.metaClass.static.findByCode = {String code ->
//            return null
//        }
//
//        when:"The save action is executed with an invalid command obj"
//        request.contentType = FORM_CONTENT_TYPE
//        request.method = 'POST'
//        params.color = 'FooBarColor'
//        controller.save()
//
//        then:"The create view is rendered again with the correct model"
//        view == 'create'
//    }

}

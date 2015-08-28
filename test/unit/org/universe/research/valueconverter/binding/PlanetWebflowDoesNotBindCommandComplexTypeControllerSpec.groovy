package org.universe.research.valueconverter.binding

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.webflow.WebFlowUnitTestMixin
import org.universe.research.CodeValueConverter
import org.universe.research.Planet
import org.universe.research.PlanetFlowController
import org.universe.research.PlanetSize
import spock.lang.Specification

@TestMixin(WebFlowUnitTestMixin)
@TestFor(PlanetFlowController)
@Mock(Planet)

/*
    This test does not work. Cannot get Spock style test to run with WebFlowUnitTestMixin
 */
class PlanetWebflowDoesNotBindCommandComplexTypeControllerSpec extends Specification{

    static doWithSpring = {
            codeValueConverter(CodeValueConverter)
    }

    void 'WebFlowTestMixinAndSpockDontPlay'(){
        assert false
    }

    void 'testFlowExecution'(){
        mockController(PlanetFlowController)
        planetFlow.init.action()
        assert conversation.flowName == 'planet'
    }

    void 'testSaveIsHappy'() {
        //What am I doing wrong here? The WebFlowTestMixin does not seem to work well with Spock.
        //https://github.com/grails-plugins/grails-webflow-plugin/blob/master/test/unit/grails/test/mixin/WebFlowUnitTestMixinTests.groovy
        given:

        PlanetSize.metaClass.static.findByCode = {String code ->
            return new PlanetSize(code: code, description: "TestPlanetSize")
        }

        when:
        params.color = 'Blue'
        params.planetSize = 'Big'//Should be bound with CodeValueConverter

        def event = planetFlow.create.on.submit.action()
        //This returns java.lang.NullPointerException: Cannot invoke method validate() on null object. The Command object is not bound.

        then:
        assert flow.planetInstance.hasErrors()


    }


//    void "Test the save action redirects to create after validation error"() {
//        given:
//        PlanetSize.metaClass.static.findByCode = {String code ->
//            return null
//        }
//
//        when:"The save action is executed with an invalid command obj"
//        request.method = 'POST'
//        params.color = 'FooBarColor'
//        controller.save()
//
//        then:"The create view is rendered again with the correct model"
//        view == 'create'
//    }

}

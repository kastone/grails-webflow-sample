package org.universe.research.validateable

import grails.test.mixin.TestFor
import grails.test.runtime.FreshRuntime
import org.universe.research.PlanetFlowNoValidateableController
import org.universe.research.WebflowPlanetNoValidateableCommand
import spock.lang.Specification

/*
This follows suggested way of testing command objects from grails docs.
http://grails.github.io/grails-doc/2.4.3/guide/testing.html#unitTestingControllers Testing Command Objects
However if the command object is used in a WebFlow, it appears you have to annotate it with @Validateable for unit tests to pass.

UPDATE: WebflowPlanetNoValidateableCommand not recognized by grails-core as command object.
 */
@TestFor(PlanetFlowNoValidateableController)
class PlanetWebflowNoValidateableControllerUsesTestForSpec extends Specification {


    void 'WebFlowCommandAndTestForAndSpockDontPlay'(){
        assert false
    }

    @FreshRuntime
    def testWebflowCommandConstraints_AllHappy() {

        when:
        //The below way along with @TestFor is suggested way to test in Grails docs 2.4.0 and higher but doesn't find validate() method on command.
        WebflowPlanetNoValidateableCommand command = new WebflowPlanetNoValidateableCommand(color:'Red', planetSize:'Big')

        then:
        assert command.validate()//This fails with No signature of method: org.universe.research.WebflowPlanetNoValidateableCommand.validate()
        assert command.hasErrors() == false
    }
}

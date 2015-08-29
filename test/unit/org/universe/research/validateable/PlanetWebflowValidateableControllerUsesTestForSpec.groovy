package org.universe.research.validateable

import grails.test.mixin.TestFor
import grails.test.runtime.FreshRuntime
import org.universe.research.PlanetFlowNoValidateableController
import org.universe.research.PlanetSize
import org.universe.research.WebflowPlanetCommand
import spock.lang.Specification

/*
This follows suggested way of testing command objects from grails docs.
http://grails.github.io/grails-doc/2.4.3/guide/testing.html#unitTestingControllers Testing Command Objects
However if the command object is used in a WebFlow, it appears you have to annotate it with @Validateable for unit tests to pass.

UPDATE: grails-core doesn't recognize WebflowPlanetCommand as a command object as not used as argument to action in controller.
 */

@TestFor(PlanetFlowNoValidateableController)
class PlanetWebflowValidateableControllerUsesTestForSpec extends Specification {

    @FreshRuntime
    def testWebflowCommandConstraints_AllHappy() {

        when:
        //The below way along with @TestFor is suggested way to test in Grails docs 2.4.0 and higher but doesn't find validate() method on command.
        WebflowPlanetCommand command = new WebflowPlanetCommand(color:'Red', planetSize: new PlanetSize(code: 'Big', desc: 'Desc'))

        then:
        assert command.validate()//This fails with No signature of method: org.universe.research.WebflowPlanetCommand.validate()
        assert command.hasErrors() == false
    }
}

package org.universe.research.validateable

import grails.test.mixin.TestFor
import grails.test.runtime.FreshRuntime
import org.universe.research.PlanetCommand
import org.universe.research.PlanetController
import org.universe.research.PlanetSize
import spock.lang.Specification

/*
This follows suggested way of testing command objects from grails docs.
http://grails.github.io/grails-doc/2.4.3/guide/testing.html#unitTestingControllers Testing Command Objects
However if the command object is used in a WebFlow, it appears you have to mark it with @Validateable for unit tests to pass.
 */

@TestFor(PlanetController)
class PlanetControllerNoValidateableCommandUsesTestForSpec extends Specification {


    @FreshRuntime
    def testCommandConstraints_AllHappy() {

        when:
        PlanetCommand command = new PlanetCommand(color:'Red', planetSize: new PlanetSize(code: 'Big', description: 'Desc'))

        then:
        assert command.validate()
        assert command.hasErrors() == false
    }

    @FreshRuntime
    def testCommandConstraints_NotHappy() {

        when:
        PlanetCommand command = new PlanetCommand(color:'Magenta', planetSize: new PlanetSize(code: 'Big', description: 'Desc'))

        then:
        assert !command.validate()
        assert command.hasErrors()
        assert command.errors.allErrors.find{it.toString().contains('not.inList.color')}
    }
}

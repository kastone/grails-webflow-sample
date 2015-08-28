package org.universe.research.validateable

import grails.test.mixin.TestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import grails.test.runtime.FreshRuntime
import org.universe.research.PlanetSize
import org.universe.research.WebflowPlanetCommand
import spock.lang.Specification

@TestMixin(ControllerUnitTestMixin)
class PlanetWebflowValidateableControllerUsesMockCommandSpec extends Specification {

    @FreshRuntime
    def testWebflowCommandConstraints_AllHappy() {

        when:

        WebflowPlanetCommand command = mockCommandObject(WebflowPlanetCommand)
        command.color = 'Red'
        command.planetSize = new PlanetSize(code: 'Big', description: 'UnitTestDesc')

        then:
        assert command.validate()
        assert command.hasErrors() == false
    }

    @FreshRuntime
    def testWebflowCommandConstraints_ColorDoesNotValidate() {

        when:

        WebflowPlanetCommand command = mockCommandObject(WebflowPlanetCommand)
        command.color = 'Chartreuse'
        command.planetSize = new PlanetSize(code: 'Big', description: 'UnitTestDesc')

        then:
        assert !command.validate()
        assert command.hasErrors() == true
        assert command.errors.allErrors.find{it.toString().contains('not.inList.color')}
    }
}

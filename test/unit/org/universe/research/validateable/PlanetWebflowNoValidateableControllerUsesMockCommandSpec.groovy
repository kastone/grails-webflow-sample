package org.universe.research.validateable

import grails.test.mixin.TestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import grails.test.runtime.FreshRuntime
import org.universe.research.WebflowPlanetNoValidateableCommand
import spock.lang.Specification

@TestMixin(ControllerUnitTestMixin)
class PlanetWebflowNoValidateableControllerUsesMockCommandSpec extends Specification {

    @FreshRuntime
    def testWebflowCommandConstraints_AllHappy() {

        when:

        WebflowPlanetNoValidateableCommand command = mockCommandObject(WebflowPlanetNoValidateableCommand)
        command.color = 'Red'
        command.planetSize ='Big'

        then:
        assert command.validate()
        assert command.hasErrors() == false
    }

    @FreshRuntime
    def testWebflowCommandConstraints_ColorDoesNotValidate() {

        when:

        WebflowPlanetNoValidateableCommand command = mockCommandObject(WebflowPlanetNoValidateableCommand)
        command.color = 'Chartreuse'
        command.planetSize ='Big'

        then:
        assert !command.validate()
        assert command.hasErrors() == true
        assert command.errors.allErrors.find{it.toString().contains('not.inList.color')}
    }
}

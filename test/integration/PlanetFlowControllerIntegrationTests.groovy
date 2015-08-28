import org.junit.Test
import org.universe.research.PlanetFlowController
import grails.test.WebFlowTestCase
import grails.test.mixin.TestMixin
import grails.test.mixin.integration.IntegrationTestMixin

@TestMixin(IntegrationTestMixin)
class PlanetFlowControllerIntegrationTests extends WebFlowTestCase{

    def getFlow(){
        return new PlanetFlowController().planetFlow
    }

    @Test
    void testWebFlowTestCaseNotWorking(){
        assert false
    }

    //TODO I think WebFlowTestCase is out of sync with Grails 2.4.3 Integration testing requirements.
    //The example for testing webflow controllers was in http://grails.github.io/grails-doc/2.3.7/guide/testing.html#integrationTesting
    //But is not in http://grails.github.io/grails-doc/2.4.3/guide/testing.html#integrationTesting

//    @Test
//    void testHappyPlanetCreateFlow(){
//        def viewSelection = startFlow()
//        assert viewSelection.viewName == "create"
//
//        flow.params.color="Red"
//        flow.params.planetSize = "Big"
//        viewSelection = signalEvent("submit")
//
//        assert viewSelection.planetInstance
//
//    }



}

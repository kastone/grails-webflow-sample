import geb.junit4.GebReportingTest

import org.junit.Test

class MorningFlowControllerTests extends GebReportingTest {

    @Test
    void testInit() {
        go 'morningFlow'
        report('Wake Up Page')
        at(StartMorningRoutinePage)

        submitButton.click()
        report('Hit Alarm Page')
        at(AlarmPage)

        linkToSameEvent.click()//this should have execution param and call the same flow event
        report('Alarm Page rendered after Increment')
        at(AlarmPage)

    }
}

import geb.Page

class AlarmPage extends Page{
    static at = {
        submitButton.value().toString().contains('Alarm')
        linkToSameEvent.text().contains('Increment')
    }

    static content = {
        submitButton  { $('input', type:"submit")}
        linkToSameEvent { $('a')}
    }
}

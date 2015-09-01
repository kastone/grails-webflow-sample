import geb.Page

class StartMorningRoutinePage extends Page{
    static at = {
        submitButton
    }

    static content = {
        submitButton  { $('input', type:"submit")}
    }
}

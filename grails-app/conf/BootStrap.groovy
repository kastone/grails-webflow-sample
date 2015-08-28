import org.universe.research.PlanetSize

class BootStrap {

    def init = { servletContext ->
        new PlanetSize(code:'Big', description:'Big Planet').save()
        new PlanetSize(code:'Small', description: 'Small Planet').save()
    }
    def destroy = {
    }
}

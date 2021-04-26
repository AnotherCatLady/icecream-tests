package de.weiss.icecreamtests

import geb.spock.GebReportingSpec

class IcecreamAT extends GebReportingSpec {

    def "test"() {
        when:
        IcecreamPage page = at IcecreamPage

        then:
        page.$().text().contains("Eis")

    }
}
package de.weiss.icecreamtests

import geb.Page

class IcecreamPage extends Page {

    static at = {
        $(id: "icecream-page").displayed
    }

    def clickDetailButton(String icecreamName) {
        def rows = $(class: "icecream-row")
        for (def row : rows) {
            if (row.text().contains(icecreamName)) {
                row.$("button").click()
            }
        }
    }

}

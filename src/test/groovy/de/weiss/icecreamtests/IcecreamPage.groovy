package de.weiss.icecreamtests

import geb.Page

class IcecreamPage extends Page {

    static at = {
        $().text().contains("Eis")
    }

}
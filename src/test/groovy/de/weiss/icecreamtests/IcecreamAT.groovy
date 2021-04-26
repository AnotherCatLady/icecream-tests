package de.weiss.icecreamtests

import geb.spock.GebReportingSpec
import spock.lang.Unroll

class IcecreamAT extends GebReportingSpec {

    final static url = "http://localhost:8080"

    @Unroll
    def "Eissorte speichern"() {
        given: "Eis Applikation ist geöffnet,"
        go url
        IcecreamPage page = at IcecreamPage

        and: "das Eingabeformular für Eissorten"
        page.$(id: "icecreamForm").displayed

        and: "die Liste der vorhandenen Eissorten werden angezeigt"
        page.$(id: "icecreamList").displayed

        when: "der Name des Eises eingegeben"
        page.$(id: "inputName") << name

        and: "die Kategorie #category ausgewählt wird, "
        page.$(id: "dropdownCategory").click()
        waitFor { page.$(id: categoryId).displayed }
        page.$(id: categoryId).click()

        then: "werden die Zusatzfelder der Kategorie im Formular angezeigt."
        if (category == "Frucht-Eis") {
            waitFor { page.$(id: "inputFruits").displayed && page.$(id: "inputFruitContent").displayed }
        } else if (category == "Sahne-Eis") {
            waitFor { page.$(id: "inputCreamContent").displayed }
        } else if (category == "Wasser-Eis") {
            waitFor { page.$(id: "inputFlavours").displayed }
        } else {
            // fail on purpose if test case isn't implemented
            false
        }

        when: "Die Zusatzfelder ausgefüllt"
        if (category == "Frucht-Eis") {
            page.$(id: "inputFruits") << fruits
            page.$(id: "inputFruitContent") << fruitContent
        } else if (category == "Sahne-Eis") {
            page.$(id: "inputCreamContent") << creamContent
        } else if (category == "Wasser-Eis") {
            page.$(id: "inputFlavours") << flavours
        }

        and: "die Zutaten"
        page.$(id: "inputIngredients") << ingredients

        and: "die Lebensmittelunverträglichkeiten"
        page.$(id: "inputIntolerances") << intolerances

        and: "der Nährwert"
        page.$(id: "inputCalories") << calories

        and: "der EK Preis"
        page.$(id: "wholesalePrice") << wholesalePrice

        and: "der VK Preis ausgefüllt"
        page.$(id: "retailPrice") << retailPrice

        and: "der Hinzufügen-Button gedrückt wird,"
        page.$(id: "buttonAddIcecream").click()

        then: "wird die neue Eissorte in der Liste der vorhandenen Eissorten angezeigt."
        waitFor { page.$(id: "icecreamList").text().contains(name) }

        when: "die Detailseite für das neue Eis aufgerufen wird"
        page.clickDetailButton(name)

        then: "wird die Detailseite für das neue Eis angezeigt"
        IcecreamDetailPage detailPage = at IcecreamDetailPage

        and: "der Name"
        detailPage.$(id: "name").text() == name

        and: "die Kategorie"
        detailPage.$(id: "category").text() == category

        and: "die Zutaten"
        detailPage.checkIngredients(ingredients)

        and: "die Lebensmittelunverträglichkeiten"
        detailPage.$(id: "intolerances").text() == intolerances

        and: "der Nährwert"
        detailPage.$(id: "calories").text() == calories + " kcal / 100g"

        if (category == "Frucht-Eis") {
            and: "der Fruchtgehalt"
            detailPage.$("fruitContent").text() == fruitContent + " %"
            and: "die Früchte"
            detailPage.checkFruits(fruits)
        } else if (category == "Sahne-Eis") {
            and: "der Sahnegehalt"
            detailPage.$("creamContent").text() == creamContent + " %"
        } else if (category == "Wasser-Eis") {
            and: "die Geschmacksstoffe"
            detailPage.checkFlavours(flavours)
        }
        and: "der EK-Preis"
        detailPage.$(id: "wholesalePrice").text() == wholesalePrice + " EUR / Liter"

        and: "der VK-Preis angezeigt."
        detailPage.$(id: "retailPrice").text() == retailPrice + " EUR / Liter"

        when: "der Zurück-Button angeklickt wird"
        page.$(id: "buttonBack").click()

        then: "werden wieder das Eissorten-Formular und die Eissorten-Liste angezeigt."
        page.$(id: "icecreamForm").displayed
        page.$(id: "icecreamList").displayed

        where:
        name                | category     | categoryId    | fruits                           | fruitContent | creamContent | flavours                     | ingredients                     | intolerances                   | calories | wholesalePrice | retailPrice
        "Bestes Kirscheis"  | "Frucht-Eis" | "optionFruit" | "Erdbeeren, Himbeeren, Zitronen" | "60"         | null         | null                         | "Milch, Zucker, Früchte"        | "enthält Milch"                | "200"    | "20"           | "40"
        "Tolles Sahneeis"   | "Sahne-Eis"  | "optionCream" | null                             | null         | "40"         | null                         | "Sahne, Haselnüsse, Schokolade" | "enthält Sahne und Haselnüsse" | "500"    | "30"           | "60"
        "Melonen-Wassereis" | "Wasser-Eis" | "optionWater" | null                             | null         | null         | "Melonenaroma, Vanillearoma" | "Wasser, Zucker, Aromastoffe"   | "enthält Aromastoffe"          | "300"    | "10"           | "30"
    }

    def "Doppelte Eissorten-Namen werden abgelehnt"() {
        given: "Eis Applikation ist geöffnet,"
        go url
        IcecreamPage page = at IcecreamPage

        and: "das Eingabeformular für Eissorten"
        page.$(id: "icecreamForm").displayed

        and: "die Liste der vorhandenen Eissorten werden angezeigt"
        page.$(id: "icecreamList").displayed

        when: "der Name des Eises eingegeben"
        page.$(id: "inputName") << "Bestes Kirscheis"

        and: "die Kategorie #category ausgewählt wird, "
        page.$(id: "dropdownCategory").click()
        waitFor { page.$(id: "optionFruit").displayed }
        page.$(id: "optionFruit").click()

        and: "der Hinzufügen-Button gedrückt wird,"
        page.$(id: "buttonAddIcecream").click()

        then: "wird eine Fehlermeldung angezeigt."
        waitFor { page.$(id: "errorMessage").displayed && page.$(id: "errorMessage").text().length() > 0 }
    }
}

package de.weiss.icecreamtests

import geb.Page

class IcecreamDetailPage extends Page {

    static at = {
        $(id: "icecreamDetails").displayed
    }

    /**
     * Checks ingredients list and returns true if all ingredients are displayed.
     * @param ingredients list of ingredients.
     * @return true if all ingredients are displayed.
     */
    boolean checkIngredients(String ingredientsInput) {
        checkChipList("Zutaten", ingredientsInput)
    }

    /**
     * Checks fruits list and returns true if all fruits are displayed.
     * @param fruits list of fruits.
     * @return true if all fruits are displayed.
     */
    boolean checkFruits(String fruitInput) {
        checkChipList("Früchte", fruitInput)
    }

    /**
     * Checks flavours list and returns true if all flavours are displayed.
     * @param flavours list of flavours.
     * @return true if all flavours are displayed.
     */
    boolean checkFlavours(String flavourInput) {
        checkChipList("Geschmacksstoffe", flavourInput)
    }

    private boolean checkChipList(String label, String list) {
        String[] items = list.split("\\s*,\\s*")
        for (def item : items) {
            if (!$("aria-label": label).text().contains(item)) {
                false
            }
        }
        true
    }

}

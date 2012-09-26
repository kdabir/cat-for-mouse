
import groovy.json.JsonSlurper

class ShortcutsJsonBuilder {
    final def json

    ShortcutsJsonBuilder(String intial_json) {
        json = new JsonSlurper().parseText(intial_json)
    }

    ShortcutsJsonBuilder(Object intial_json_slurper_object) {
        json = intial_json_slurper_object
    }

    def merge(other_json){

        other_json.each { other_context_group_name, other_shortcuts_set ->

            if (this.json."$other_context_group_name"){ // json has same group
                other_shortcuts_set.each { other_action_keywords, other_shortcut ->

                    def shortcut = json."$other_context_group_name"."$other_action_keywords"

                    if (shortcut) { // json has some shortcuts for other_action_keywords
                        if (other_shortcut) { //other_json also has some shortcut for this action, merge them
                            json."$other_context_group_name"."$other_action_keywords" << other_shortcut

                        }
                    } else { // insert the other_json's shortcut in json
                        json."$other_context_group_name"."$other_action_keywords" = other_shortcut
                    }
                }
            } else {
                json."$other_context_group_name" = other_shortcuts_set
            }

        }
    }

}





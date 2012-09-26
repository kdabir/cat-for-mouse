//////////// set these variables ////////////
def filename = "../text/word.mac.txt"
def out_filename = "../word.json"
def os = "mac"
def split_regex = /\t/
/////////////////////////////////////////////

def current_context = ""
def out = [:]
def json = new groovy.json.JsonBuilder()

new File(filename).getText('UTF-8').eachLine { line ->
    if (line=~ split_regex) {
		def arr = line.split(/\t/)
		out."$current_context" << ["${arr[0].trim()}":["${os}":arr[1].trim()]]
	} else if (line) {
		current_context = line.trim()
		out."$current_context" = [:]
	}
}
json  out

new File(out_filename).setText(json.toPrettyString(), "utf8")


/// \: \"(.+)\",
/// : { "mac": "$1"},

import groovy.json.JsonBuilder

def current_context = ""
def out = [:]

def parsed = [:]
def slurper = new groovy.json.JsonSlurper()

def builder = new ShortcutsJsonBuilder("{}")


def file_prefix = "chrome"
['mac.json','win.json','linux.json'].each { ext->
	def file = new File("$file_prefix.$ext")
	if (file.exists()){
		//parsed[file.name] = slurper.parseText(file.text)
        builder.merge(slurper.parseText(file.text))
	}
}


def json = new JsonBuilder()
json builder.json
println json.toPrettyString()
//println parsed.size()

//json_out.result  parsed

//println json_out.toPrettyString()
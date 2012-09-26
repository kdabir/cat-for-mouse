
def json1 = new groovy.json.JsonSlurper().parseText("""
{
    "same group in both": {
        "Opens a new window.": {"mac":"\u2318-N"},
        "Opens a new tab.": {},
        "exact same in both": {"mac":"h then g"},
        "new in json1": "Q",
    },
    "Extra group in json1": {
        "some key": {"win":"abc"}
    }
}
""")

def json2 = new groovy.json.JsonSlurper().parseText("""
{
    "same group in both": {
        "Opens a new window.": {"win":"CTRL+N"},
        "Opens a new tab.": {"win":"CTRL+T"},
        "exact same in both":  {"win":"h then g"},
        "new in json2": {"win":"Win + Pause"},
    },
    "Extra group in json2": {
        "some key": {"mac":"def"}
    }
}
""")

assert json1.size() == 2

def builder = new ShortcutsJsonBuilder(json1)
builder.merge(json2)
def merged_json = builder.json

assert merged_json.size() == 3
assert merged_json."Extra group in json2"
assert merged_json["same group in both"].size()==5


def json = new groovy.json.JsonBuilder()

json  merged_json
println json.toPrettyString()
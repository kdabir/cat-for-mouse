# Shortcuts File Database
==========================
This directory stores the shortcuts database in plain-text files. The supported file format is `json`. You are welcome to fork this repo and add more shortcuts. *Keeping it simple* is the main philosophy behind the structure thats in place as of now. To answer a few questions that might raise in your mind, following is a simple FAQs section.

FAQs
----

## Why files and not a real database?
Well this serves as a 'real' database for us. Why we preferred text files based database over 'real' 'real' database because text files are: 

1. human readable
2. easy to edit without special tools

## Why Json Files?
json files are used to store the shortcuts mappings as they are: 

1. well understood by most
2. defacto format for webapps
3. contain low markup noise 

## Why host the shortcuts database on Github?
This frees us from maintaining a seperate database server for shortcuts and provides a very easy and safe way for community to edit files and add new shortcuts by using github. We use Github API to access these shortcuts files in our application to read shortcuts.

## How Do I add shortcuts for my favorite application?
To add new shortcuts, fork this repo, add the shortcuts file to this directory, commit and send a pull request. The file specifications are provided below.

### File Name
Each supported Application should have it's corresponding `.json` file in this directory. The name of the file should be application name, 
eg. `chrome.json`. lowercase names preferred.

### File Format
The format should be :

`appname.json`

    {
        "Context Name to which the shortcuts set belongs, eg. Edit": {
            "Undo Typing": { "mac": "Cmd Z", "win" : "CTRL Z", "linux": "CTRL Z"},
            "More shortcuts in this context group": { "mac": "?", "win" : "?", "linux": "?"}
        },
        "Context Name to which these set of shortcuts belongs, eg. Navigation": {
            "Shortcuts belonging to this context group, eg. Go to File": { "mac": "Cmd O", "win" : "CTRL O", "linux": "CTRL O"},
            "More shortcuts in this context group": { "mac": "#", "win" : "#", "linux": "#"}
        }
    }

Note the value of the any given shortcut command/action should explicitly specify shortcut on each supported platform
    
        { "mac": "Cmd Z", "win" : "CTRL Z", "linux": "CTRL Z"},

And the accepted keys for platform are `mac`, `win` and `linux` for now.
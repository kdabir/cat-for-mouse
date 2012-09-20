import org.c4m.service.ShortcutService
import org.c4m.repository.GithubShortcutsJsonRepository
import org.c4m.service.Platform
import org.c4m.repository.LocalShortcutsJsonRepository
import groovy.json.JsonBuilder

class Main {


    public static void main(String[] args) {
        //def allApps =  service.allApps
        if (args.length < 2){
            println "provide valid shortcuts dir and output dir"
            println "command shortcutsDir outputDir"
            return
        }

        ShortcutService service = new ShortcutService(new LocalShortcutsJsonRepository(args[0]))

        Platform.values().each { platform ->
            def platformDir = args[1] + "/" + platform.toString()
            new File(platformDir).mkdir()

            def apps = service.getAppsFor(platform)
            apps.each { appName ->
                JsonBuilder json = new JsonBuilder()
                json service.getShortcutsFor(appName,platform)
                new File("${platformDir}/${appName}.json").text  = json.toPrettyString()
            }

        }

    }

}


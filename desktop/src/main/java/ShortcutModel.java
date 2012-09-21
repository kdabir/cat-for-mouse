/**
 * Created with IntelliJ IDEA.
 * User: poojaak
 * Date: 21/09/12
 * Time: 7:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShortcutModel {
    private String action;
    private String shortCut;

    public ShortcutModel(String action, String shortCut) {
        this.action = action;
        this.shortCut = shortCut;
    }

    public String getAction() {
        return action;
    }

    public String getShortCut() {
        return shortCut;
    }
}

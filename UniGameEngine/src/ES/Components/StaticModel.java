
package ES.Components;

import GameEngine.Model;

/**
 *
 * @author jonathan
 */
public class StaticModel extends Component{
    private final Model model;
    private boolean visible = true;

    public StaticModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
}

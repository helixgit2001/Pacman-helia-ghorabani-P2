package gameplay.scene;

import engine.core_kernel.Map;
import engine.ui.SceneController;
import engine.ui.View;

/**
 * Class used to manage menu views
 */
public class MenuController implements SceneController {
    private View view;

    public MenuController(){
    }

    @Override
    public void init() {
        view = new MenuView(500,500);
    }

    @Override
    public void update(Map map) {

    }

    public View getView() {
        return view;
    }
}

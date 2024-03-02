package GUI;

import Graphical.objectsRegister;
import fileSaver.FileSaver;
import fileSaver.RestartReader;
import java.io.File;
import java.io.IOException;

//a class that is used as button response
public class RestartButton extends ButtonResponse {

    public TimeDisplay timer;
    public Button play;
    objectsRegister obj;

    public RestartButton(TimeDisplay timerV, Button playV, objectsRegister objV) {
        super();
        // gets all needed details to save objects at the right moment
        this.timer = timerV;
        this.play = playV;
        this.obj = objV;
    }

    // this restarts the objects from the restart file
    public void activate() {
        this.Active = !this.Active;
        this.restart(this.obj);
    }

    public void restart(objectsRegister obj) {
        // if the play button is not pressed, then it restarts it
        if (!this.play.response.getActive()) {
            obj.clear();
            this.timer.restart();
            // try statement needed to ensure that Restart file is available
            try {
                RestartReader.restartFromFile(obj, new File("src/RestartFile"));
            } catch (IOException e) {
            }
            obj.restartFreeForces();
        } else {
            this.play.response.activate();
            // stops the simulation and restarts it
            this.restart(obj);
        }
    }

    public void Save(objectsRegister obj) {
        // saves all objects in the object register given
        try {
            FileSaver.SaveTo(new File("src/RestartFile"), obj);
        } catch (IOException e) {
        }
    }

    // checks whether it needs to save something or not
    public void update(objectsRegister obj) {
        // only if both timer is 0 and the object queue
        // has been changed it saves the object and queue as well
        if (obj.isChanged() && this.timer.getTime() == 0) {
            this.Save(obj);
            obj.Save();
        }
    }

}

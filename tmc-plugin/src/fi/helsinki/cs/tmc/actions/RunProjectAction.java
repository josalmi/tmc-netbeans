package fi.helsinki.cs.tmc.actions;

import fi.helsinki.cs.tmc.data.Exercise;
import fi.helsinki.cs.tmc.model.CourseDb;
import fi.helsinki.cs.tmc.model.ProjectMediator;
import fi.helsinki.cs.tmc.model.TmcProjectInfo;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.ui.support.ProjectSensitiveActions;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;

@ActionID(category = "TMC", id = "fi.helsinki.cs.tmc.actions.RunProjectAction")
@ActionRegistration(displayName = "#CTL_RunProject", lazy = false)
@ActionReferences({
    @ActionReference(path = "Menu/TM&C", position = -5, separatorAfter = 0),
    @ActionReference(path = "Projects/Actions", position = 1350, separatorBefore = 1330,
            separatorAfter = 1360) // Positioning y u no work?
})
@NbBundle.Messages("CTL_RunProject=Run Project")
public class RunProjectAction extends AbstractExerciseSensitiveAction {

    private CourseDb courseDb;
    private ProjectMediator projectMediator;

    public RunProjectAction() {
        this.courseDb = CourseDb.getInstance();
        this.projectMediator = ProjectMediator.getInstance();

        putValue("noIconInMenu", Boolean.TRUE);
    }

    @Override
    protected CourseDb getCourseDb() {
        return courseDb;
    }

    @Override
    protected ProjectMediator getProjectMediator() {
        return projectMediator;
    }

    @Override
    protected void performAction(Node[] nodes) {
        for (TmcProjectInfo project : projectMediator.wrapProjects(projectsFromNodes(nodes))) {
            Exercise exercise = projectMediator.tryGetExerciseForProject(project, courseDb);
            if (exercise == null) {
                continue;
            }
            exercise.setRun(true);
        }
        
        ProjectSensitiveActions.projectCommandAction(ActionProvider.COMMAND_RUN, "Run Project", null).actionPerformed(null);
    }

    @Override
    public String getName() {
        return "Run Project";
    }
    
    @Override
    protected String iconResource() {
        // The setting in layer.xml doesn't work with NodeAction
        return "org/netbeans/modules/project/ui/resources/runProject.png";
    }

}

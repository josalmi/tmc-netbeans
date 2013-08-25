package fi.helsinki.cs.tmc.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;

public class TmcFileUtils {
    private static final Logger log = Logger.getLogger(TmcFileUtils.class.getName());
    
    /**
     * If the file object is owned by a project, returns the path of the file
     * relative to the project.
     * 
     * Otherwise returns the entire path.
     */
    public static String getPathRelativeToProject(FileObject fileObject) {
        String filePath = fileObject.getPath();
        
        try {
            Project p = FileOwnerQuery.getOwner(fileObject);
            String projectDirectory = p.getProjectDirectory().getPath();
            if (filePath.contains(projectDirectory)) {
                filePath = filePath.substring(filePath.indexOf(projectDirectory) + projectDirectory.length());
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "Unable to determine project path for file {0}.", filePath);
        }
        
        return filePath;
    }
}

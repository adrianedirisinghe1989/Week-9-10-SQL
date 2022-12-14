package projects.service;




import projects.dao.ProjectDao;
import projects.entity.Project;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import projects.exception.DbException;


public  class ProjectService  {
	private static final String SCHEMA_FILE ="projects-schema.sql";
	
private  ProjectDao ProjectDao = new ProjectDao();

public List<Project>fetchProjects(){
	return ProjectDao.fetchAllProjects();
}

public Project fetchProjectById(Integer projectId) {
	return ProjectDao.fetchProjectById(projectId).orElseThrow(()-> 
	new NoSuchElementException("Project with Id = " + projectId + " does not exist."));
}


//public projects fetchProjectById(Integer projectId) {
//	return projectDao.fetchProjectById(projectId).orElseThrow(()-> new 
//	NoSuchElementException("Project with project ID=" + projectId + "does not exist."));
//		
//}


public Project addProject(Project project) {
return ProjectDao.insertProject(project);
}

public void createAndPoupulateTables() {
	loadFromFile(SCHEMA_FILE);
}



private void loadFromFile(String fileName) {
	String content = readFileContent(fileName);
	List<String>sqlStatements = convertContentToSqlStatements(content);

	
	sqlStatements.forEach(line  -> System.out.println(line));
	
	ProjectDao.executeBatch(sqlStatements);
}


private List<String> convertContentToSqlStatements(String content) {
	content = removeComments(content);	content = replaceWhitespaceSequencesWithSingleSpace(content);
	
	return extractLinesFromContent(content);
}
	
private List<String> extractLinesFromContent(String content) {
	List<String> lines= new LinkedList<String>();
	
	while(!content.isEmpty()) {
		int semicolon = content.indexOf(";");
		
	if(semicolon ==-1 ) {
			if(!content.isBlank()) {
				lines.add(content);
			}
		
			content = "";
		}
		else {
			lines.add(content.substring(0,semicolon).trim());
			content = content.substring(semicolon +1);
		}

	}
	return lines;
}

private String replaceWhitespaceSequencesWithSingleSpace(String content) {
	return content.replaceAll("\\s+", " ");
}

private String removeComments(String content) {
	StringBuilder builder = new StringBuilder (content);
	int commentPos =0;
	
	while((commentPos = builder.indexOf(" --", commentPos)) != -1) {
		int eolPos = builder.indexOf("\n", commentPos+1);
		
		if(eolPos== -1) {
			builder.replace(commentPos, builder.length(), "");
		}
		else {
			builder.replace(commentPos, eolPos+1, "");
		}
	}
	
	return builder.toString();
}

private String readFileContent(String fileName) {
	try {
		Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());	
		return Files.readString(path);
		
	} catch (Exception e) {
		throw new DbException(e);
	}	
}
}




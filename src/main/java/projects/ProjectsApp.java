package projects;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;


public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;
	
	//@formatter:off
	private List<String> operations = List.of(
			"1) Add a project",
			"2) Create and populate all tables" ,                          
			"3) Select Projects",
			"4) Select working project"
		);	
		//@formatter:on
	public static void main(String[] args) {
		new ProjectsApp().processUserSelections();	
	}

	private void processUserSelections() {
		boolean done = false;
		
		while(!done) {	
			try {
			int selection= getUsersSelection();
				
			switch(selection) {
			case -1:
				done =exitMenu();
				break;
				
			case 1:
				addProject();                                       
				break;
				
			case 2:
				createProjects();
				break;
			case 3: 
				selectProjects();
				break;
			case 4:
				setCurrentProject();
				break;
				default:
					System.out.println("\n"+ selection + " " + "is not vaild. Try again.");
					break;
			}
			
		}catch(Exception e) {
			System.out.println("\nError:"+ e.toString()+ "Try again.");
		}
		}
	}
	private void setCurrentProject() {
		 selectProjects();
		
		Integer projectId= getIntInput(" Select a project ID");
		
		curProject = null;
		
//		for(projects project: projects) {
//			if(project.getProjectID().equals(projectId)) {
		curProject = projectService.fetchProjectById(projectId);
//				break;
//			}
//		}
//		if(Objects.isNull(curProject)) {
//			System.out.println("\nInvalid project selected. ");
//		}
//		
		
	}
	


//	private void  selectProjects() {
//		List<projects> Project = projectService.fetchProjects();
//		System.out.println("\nProjects:");	
//		projects.forEach(projects-> System.out
//				.println("  " + projects.getProjectId()+ ":"+ projects.getProjectName()));
//	
//	}

	
	
	private void selectProjects() {
		List<Project> projects = projectService.fetchProjects();
		System.out.println("\nProjects: ");
		projects.forEach(project-> System.out.println("    " + project.getProjectId()
				+ ": "+ project.getProjectName()));
		
	}
	private void addProject() {
	String projectName = getStringInput(" Enter the project name");
	BigDecimal estimatedHours = getDecimalInput( "Enter the estimated hours");
	BigDecimal actualHours= getDecimalInput("Enter the actual Hours");
	Integer difficulty = getIntInput("Enter the project difficulty(1-5)");
	String notes = getStringInput("Enter the project notes");	
	
//	BigDecimal estimatedTime = getDecimalInput(estimatedHours);
//	BigDecimal actualTime = getDecimalInput(actualHours);
	
	Project project = new Project();
	
	project.setProjectName(projectName);
	project.setEstimatedHours(estimatedHours);
	project.setActualHours(actualHours);
	project.setDifficulty(difficulty);
	project.setNotes(notes);
	
	Project dbProject = projectService.addProject(project);
	System.out.println("You have successfully created project:\n"+ dbProject);
	
	curProject = projectService.fetchProjectById(dbProject.getProjectId());
	
	}

//	private BigDecimal getDecimalInput(BigDecimal estimatedHours) {
//		int min = Objects.isNull(estimatedHours) ? 0 : estimatedHours;
//		int hours =min/60;
//		int minutes = min % 60;	return BigDecimal.valueOf(hours,minutes);
//	}

	private BigDecimal getDecimalInput(String prompt) {
		String input = getStringInput(prompt);
		if(Objects.isNull(input)) {
		return null;
	}
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch(NumberFormatException e) {
			throw new DbException(input+ "is not a valid decimal number.");
		}	
		}

	private boolean exitMenu() {
		System.out.println("\nExiting the menu. TTFN!");
	return true;
	}

	private int getUsersSelection() {
		printOperations();
		Integer input= getIntInput ("\nEnter and operation number(Press Enter to quit)");
		return Objects.isNull(input)? -1 :input;	
	}

	
	private void printOperations() {
		System.out.println("\nThese are the available selections. Press the Enter Key to quit:");
		
		operations.forEach(line -> System.out.println(" "+ line));
		
		if(Objects.isNull(curProject)) {
			System.out.println("\nYou are not working with a project.");
		}
		else {
			System.out.println("\nYou are working with project:" + curProject);
		}
	}
//private void printOperations() {
//	System.out.println();
//	System.out.println("Here's what you can do:");
//	operations.forEach(line -> System.out.println("  "+ line));
//
//		
//	}


private void createProjects() {
	projectService.createAndPoupulateTables();
	System.out.println("\nTables created and pouplated!");
	
}
	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
	}
		try {
		return Integer.parseInt(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input+ " is not a valid number.");
		}
	}
	private String getStringInput(String prompt) {
	System.out.print(prompt+ " :");
	String line = scanner.nextLine();	
	return line.isBlank()? null : line.trim();	
}
	private Double getDoubleInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
			return null;
	}
		try {
		return Double.parseDouble(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input+ " is not a valid number.");
		}
	}
}
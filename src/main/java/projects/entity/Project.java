package projects.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Project {
	private Integer projectId;
	private String projectName;
	private String notes;
	private Integer difficulty;
	private BigDecimal estimatedHours;
	private BigDecimal actualHours;
	private LocalDateTime createdAt;
	
	

	private List<material> materials = new LinkedList();
	private List<Step> steps = new LinkedList<>();
	private List<Category> categories = new LinkedList<>();
	@Override
	public String toString() {
	DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-YYYY HH:mm");
	String createTime = Objects.nonNull(createdAt)? fmt.format(createdAt) : "(null)";
	String project =" ";
	
	project += "\n     Id=" + projectId;
	project += "\n     projectName=" + projectName;
	project += "\n     notes=" + notes;
	project += "\n     difficulty=" + difficulty;
	project += "\n     estimatedHours=" + estimatedHours;
	project += "\n     actualHours=" + actualHours;
	project += "\n     createdAt=" + createTime;
	
	project += " \n Materials:";
	
	for( material  Materials : materials) {
		project += 	"\n  "+ Materials;
	}
	project += 	"\n Steps:";
	
	for( Step  step : steps) {
		project +=		"\n  "+ step;
	}
project += "\n categories:";
	
	for( Category  category : categories) {
		project += "\n  "+ category;
	}	
	return project;
	
	}
	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	public BigDecimal getEstimatedHours() {
		return estimatedHours;
	}
	public void setEstimatedHours(BigDecimal estimatedHours) {
		this.estimatedHours = estimatedHours;
	}
	public BigDecimal getActualHours() {
		return actualHours;
	}
	public void setActualHours(BigDecimal actualHours) {
		this.actualHours = actualHours;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public List<material> getMaterials() {
		return materials;
	}
	public List<Step> getSteps() {
		return steps;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public static void add(Project extract) {
		// TODO Auto-generated method stub
		
	}
	
		
	}
	
		
	
	



package projects.dao;




import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import projects.entity.Category;
import projects.entity.Step;
import projects.entity.material;
import projects.entity.Project;
import projects.exception.DbException;
import provided.util.DaoBase;

public class ProjectDao extends DaoBase {
	
	private static final String CATEGORY_TABLE = "category";
	private static final String MATERIAL_TABLE = "material";
	private static final String PROJECT_TABLE = "project";
	private static final String PROJECT_CATEGORY_TABLE = "project_category";
	private static final String STEP_TABLE = "step";
	
	public Optional<Project> fetchProjectById(Integer projectId){
		String sql = "SELECT * FROM " + PROJECT_TABLE +  " WHERE project_id = ?";
		//System.out.println(sql);
		try(Connection conn= DbConnection.getConnection()){
			startTransaction(conn);
			
			try {
				Project project = null;
				
				
				try(PreparedStatement stmt = conn.prepareStatement(sql)){
					setParameter(stmt, 1, projectId, Integer.class);
					
					try(ResultSet rs= stmt.executeQuery()){
						if(rs.next()) {
							project= extract(rs, Project.class);
						}
					}
				}
				if(Objects.nonNull(project)) {
					project.getMaterials().addAll(fetchProjectMaterials(conn, projectId));
					project.getSteps().addAll(fetchProjectSteps(conn,projectId));
					project.getCategories().addAll(fetchProjectCategories(conn, projectId));
				}
				commitTransaction(conn);
				return Optional.ofNullable(project);
			}
			catch(Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
		}
		catch(SQLException e) {
			throw new DbException(e);
		}
	}
	
	private List<Category> fetchProjectCategories(Connection conn, 
			Integer projectId) throws SQLException {
		//@formatter:off
	      String sql= ""
	    	+ "SELECT c.* FROM " + CATEGORY_TABLE +  " c "
	    	+ "JOIN " + PROJECT_CATEGORY_TABLE + " pc USING (category_id) "
	    	+ "WHERE project_id = ?";
	   //@formatter:on
	      
	      try(PreparedStatement stmt = conn.prepareStatement(sql)){
	    	  setParameter(stmt, 1, projectId, Integer.class);
	    	  
	    	  try(ResultSet rs= stmt.executeQuery()){
	    		  List<Category> categories = new LinkedList<Category>();
	    		  
	    		  while(rs.next()) {
	    			  categories.add(extract(rs,Category.class));
	    		  }
	    			return categories;
	    	  }
	      
	}
	
	}

	private List<Step> fetchProjectSteps(Connection conn, Integer projectId) 
	throws SQLException{
		String sql =" SELECT * FROM " + STEP_TABLE + " WHERE project_id = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			setParameter(stmt, 1, projectId, Integer.class);
			try(ResultSet rs= stmt.executeQuery()){
				List<Step>steps = new LinkedList<Step>();
				
				while(rs.next()) {
					steps.add(extract(rs, Step.class));	
				}
				return steps;
			}
		}
		
		
	}

	private List< material> fetchProjectMaterials(Connection conn, 
			Integer projectId) throws SQLException {
		String sql ="SELECT * FROM " + MATERIAL_TABLE + " WHERE project_id = ?";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			setParameter(stmt, 1, projectId, Integer.class);
			
		try(ResultSet rs= stmt.executeQuery()){
			List<material> materials = new LinkedList<>();	
			
			while(rs.next()) {
			material Materials =  extract(rs, material.class);
			
			materials.add(Materials);
				
			}
			return materials;
		}
		}
		
	}    
	

	public static List<Project> fetchAllProjects() {
	String sql = "SELECT * FROM " + PROJECT_TABLE + " ORDER by project_name";
	
	try(Connection conn = DbConnection.getConnection()){
		startTransaction(conn);
		
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			try(ResultSet rs= stmt.executeQuery()){
			List<Project> Project = new LinkedList<>();
			while (rs.next()) {
//				Project.add(extract(rs, Project.class));
				
				Project project = new Project();
				
				project.setProjectId(rs.getInt("project_id"));
				project.setProjectName(rs.getString("project_name"));
				
				Project.add(project);
			}
			return Project;
		}
			
		}
	catch (Exception e) {
		rollbackTransaction(conn);
		throw new DbException(e);	
	}
	} catch(SQLException e) {
		throw new DbException(e);	
	}
}
	

	
	public static Project insertProject(Project projects) {
		//@formatter:off
		String sql= " "
				+"INSERT INTO " + PROJECT_TABLE + " "
				+"(project_name, estimated_hours, actual_hours, difficulty, notes)"
				+"VALUES "
				+"(?, ?, ?, ?, ?)";
		//@formatter:on		
		
		try(Connection conn =DbConnection.getConnection()){
			startTransaction(conn);
		
			try(PreparedStatement stmt =conn.prepareStatement(sql)){
				setParameter(stmt, 1, projects.getProjectName(), String.class);
			    setParameter(stmt, 2, projects.getEstimatedHours(), BigDecimal.class);
			    setParameter(stmt, 3, projects.getActualHours(), BigDecimal.class);
			    setParameter(stmt, 4, projects.getDifficulty(), Integer.class);
			    setParameter(stmt, 5, projects.getNotes(), String.class);
			    
			    stmt.executeUpdate();
				
				Integer ProjectId= getLastInsertId(conn, PROJECT_TABLE);
				commitTransaction(conn);
				projects.setProjectId(ProjectId);
				return projects;
			}
			catch(Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
		} catch (SQLException e) {
		throw new DbException(e);
		}
	}
	public void executeBatch(List<String> sqlBatch) {
	try(Connection conn =DbConnection.getConnection()){
		startTransaction(conn);
	try(Statement stmt=conn.createStatement()){
			for(String sql: sqlBatch) {
					stmt.addBatch(sql);
				}
			stmt.executeBatch();
			commitTransaction(conn);
	}catch(Exception e) {
		rollbackTransaction(conn);
		throw new DbException(e);
		
	}
	} catch (SQLException e1) {
		throw new DbException(e1);
		
	}
	}

}
	






















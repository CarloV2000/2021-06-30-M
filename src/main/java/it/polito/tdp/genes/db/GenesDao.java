package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.CoppiaA;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interaction;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	public List<Integer> getAllCromosomi() {
		
		String sql = "SELECT DISTINCT g.Chromosome\n"
				+ "FROM genes g\n"
				+ "WHERE g.Chromosome!=0";
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				int cromosoma = res.getInt("Chromosome");
					result.add(cromosoma);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	

	public double getWeight(int crom1, int crom2) {
		
		String sql = "SELECT SUM(Expression_Corr)AS peso "
				+ "FROM( "
				+ "SELECT i.Expression_Corr "
				+ "FROM genes g1, genes g2, interactions i "
				+ "WHERE g1.GeneID = i.GeneID1 AND g2.GeneID = i.GeneID2 "
				+ "AND g1.Chromosome = ? AND g2.Chromosome = ? "
				+ "GROUP BY g1.GeneID, g2.GeneID)AS t ";
		Connection conn = DBConnect.getConnection();
		double peso = 0.0;
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, crom1);
			st.setInt(2, crom2);
			ResultSet res = st.executeQuery();
			if (res.first()) {
				peso = res.getDouble("peso");
			}
			res.close();
			st.close();
			conn.close();
			return peso;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	public List<CoppiaA> getAllArchi(){
		String sql = "SELECT DISTINCT g1.Chromosome AS c1, g2.Chromosome AS c2 "
				+ "FROM genes g1, genes g2, interactions i "
				+ "WHERE g1.GeneID = i.GeneID1 AND g2.GeneID = i.GeneID2 AND g1.Chromosome !=0 AND g2.Chromosome!=0 "
				+ "GROUP BY c1, c2 "
				+ "HAVING c1!=c2 ";
		List<CoppiaA>result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Integer cro1 = res.getInt("c1");
				Integer cro2 = res.getInt("c2");
				double peso = this.getWeight(cro1, cro2);
				CoppiaA arco = new CoppiaA(cro1, cro2, peso);
				result.add(arco);
				
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
}
